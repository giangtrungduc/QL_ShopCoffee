package com.example.ql_shopcoffee.Services;

import com.example.ql_shopcoffee.DAO.*;
import com.example.ql_shopcoffee.Models.discount.DiscountStrategy;
import com.example.ql_shopcoffee.Models.enums.InvoiceStatus;
import com.example.ql_shopcoffee.Models.enums.TableStatus;
import com.example.ql_shopcoffee.Models.models.*;

import java.time.LocalDateTime;
import java.util.List;

public class OrderService {
    private final InvoiceDAO invoiceDAO;
    private final OrderItemDAO orderItemDAO;
    private final ProductDAO productDAO;
    private final TableDAO tableDAO;
    private final UserDAO userDAO;

    public OrderService() {
        this.invoiceDAO = new InvoiceDAO();
        this.orderItemDAO = new OrderItemDAO();
        this.productDAO = new ProductDAO();
        this.tableDAO = new TableDAO();
        this.userDAO = new UserDAO();
    }

    public Invoice createOrderForTable(int tableNumber, String username) {
        Table table = tableDAO.findByNumber(tableNumber);
        if (table.getTableStatus() != TableStatus.AVAILABLE) {
            System.err.println("Bàn " + tableNumber + " đang bận.");
            return null;
        }

        User user = userDAO.findByUsername(username);
        String invoiceId = "HD" + System.currentTimeMillis(); // Cách tạo ID đơn giản
        Invoice invoice = new Invoice(invoiceId, user, table, LocalDateTime.now(), InvoiceStatus.PENDING);

        invoiceDAO.save(invoice);
        tableDAO.updateStatus(tableNumber, TableStatus.OCCUPIED);

        return invoice;
    }

    public boolean addItemToOrder(String invoiceId, String productId, int quantity) {
        Product product = productDAO.findById(productId);
        int availableForSale = product.getQuantityOnHand() - product.getQuantityReserved();

        if (quantity > availableForSale) {
            System.err.println("Không đủ hàng cho sản phẩm: " + product.getName());
            return false;
        }

        productDAO.updateStock(productId, 0, quantity); // Tăng số lượng tạm giữ
        OrderItem item = new OrderItem(0, product, quantity, product.getPrice());
        orderItemDAO.save(item, invoiceId);

        return true;
    }

    /**
     * Cập nhật số lượng của một món trong hóa đơn.
     * @param orderItemId ID của món hàng (trong bảng order_items).
     * @param newQuantity Số lượng mới.
     * @return true nếu thành công, false nếu thất bại.
     */
    public boolean updateItemQuantity(int orderItemId, int newQuantity) {
        // Nếu số lượng mới là 0 hoặc âm, thì coi như là xóa món đó.
        if (newQuantity <= 0) {
            return removeItemFromOrder(orderItemId);
        }

        // Tìm món hàng cần cập nhật
        OrderItem currentItem = orderItemDAO.findById(orderItemId);
        if (currentItem == null) {
            System.err.println("Không tìm thấy món hàng với ID: " + orderItemId);
            return false;
        }

        // Tính toán lượng thay đổi
        int quantityChange = newQuantity - currentItem.getQuantity();

        // Kiểm tra kho nếu số lượng tăng lên
        if (quantityChange > 0) {
            Product product = productDAO.findById(currentItem.getProduct().getId());
            int availableForSale = product.getQuantityOnHand() - product.getQuantityReserved();
            if (quantityChange > availableForSale) {
                System.err.println("Không đủ hàng để tăng số lượng cho: " + product.getName());
                return false;
            }
        }

        // Cập nhật lại kho (điều chỉnh lượng tạm giữ)
        productDAO.updateStock(currentItem.getProduct().getId(), 0, quantityChange);

        // Cập nhật lại số lượng trong CSDL
        currentItem.setQuantity(newQuantity);
        orderItemDAO.update(currentItem);

        return true;
    }

    /**
     * Xóa một món hàng ra khỏi hóa đơn.
     * @param orderItemId ID của món hàng cần xóa.
     * @return true nếu thành công, false nếu thất bại.
     */
    public boolean removeItemFromOrder(int orderItemId) {
        // Tìm món hàng cần xóa để biết số lượng cần hoàn trả
        OrderItem itemToRemove = orderItemDAO.findById(orderItemId);
        if (itemToRemove == null) {
            System.err.println("Không tìm thấy món hàng để xóa với ID: " + orderItemId);
            return false;
        }

        // Hoàn trả lại số lượng đã tạm giữ về kho
        // Ví dụ: đang tạm giữ 5, xóa đi thì lượng tạm giữ phải giảm đi 5
        productDAO.updateStock(itemToRemove.getProduct().getId(), 0, -itemToRemove.getQuantity());

        // Xóa món hàng khỏi CSDL
        orderItemDAO.delete(orderItemId);

        return true;
    }

    public void cancelOrder(String invoiceId) {
        // --- BẮT ĐẦU TRANSACTION ---
        Invoice invoice = invoiceDAO.findById(invoiceId);
        List<OrderItem> items = orderItemDAO.findByInvoiceId(invoiceId);

        for (OrderItem item : items) {
            Product product = productDAO.findById(item.getProduct().getId());
            // Hoàn trả số lượng đã tạm giữ
            productDAO.updateStock(product.getId(), 0, -item.getQuantity());
        }

        invoice.setStatus(InvoiceStatus.CANCELLED);
        invoiceDAO.update(invoice);

        Table table = tableDAO.findByNumber(invoice.getTable().getTableNumber());
        tableDAO.updateStatus(table.getTableNumber(), TableStatus.AVAILABLE);
        // --- KẾT THÚC TRANSACTION (COMMIT/ROLLBACK) ---
    }

    public boolean checkout(String invoiceId, DiscountStrategy discountStrategy) {
        // --- BẮT ĐẦU TRANSACTION ---
        try {
            Invoice invoice = invoiceDAO.findById(invoiceId);
            if(invoice == null || invoice.getStatus() != InvoiceStatus.PENDING) {
                System.err.println("Hóa đơn không hợp lệ để thanh toán.");
                return false;
            }

            List<OrderItem> items = orderItemDAO.findByInvoiceId(invoiceId);
            // Để lấy được thông tin đầy đủ của product, ta cần gọi lại productDAO
            for(OrderItem item : items) {
                item.setProduct(productDAO.findById(item.getProduct().getId()));
            }

            double subTotal = 0;
            for (OrderItem item : items) {
                subTotal += item.getPriceAtOrder() * item.getQuantity();
                // Giảm số lượng tồn kho và tạm giữ
                productDAO.updateStock(item.getProduct().getId(), -item.getQuantity(), -item.getQuantity());
            }

            invoice.setSubTotal(subTotal);
            double finalTotal = discountStrategy.applyDiscount(subTotal);
            invoice.setFinalTotal(finalTotal);
            invoice.setStatus(InvoiceStatus.PAID);

            invoiceDAO.update(invoice);
            tableDAO.updateStatus(invoice.getTable().getTableNumber(), TableStatus.AVAILABLE);

            // --- COMMIT TRANSACTION ---
            System.out.println("Thanh toán thành công! Tổng tiền: " + finalTotal);
            return true;
        } catch (Exception e) {
            // --- ROLLBACK TRANSACTION ---
            System.err.println("Thanh toán thất bại! Đang hoàn tác... " + e.getMessage());
            // Cần có logic để hoàn tác các lệnh updateStock đã gọi
            return false;
        }
    }
}
