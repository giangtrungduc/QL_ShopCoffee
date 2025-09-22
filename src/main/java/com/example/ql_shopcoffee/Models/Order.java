package com.example.ql_shopcoffee.Models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/* Lớp Order đại diện cho một đơn hàng của khách */
public class Order {
    private int orderID; // Mã đơn hàng
    private List<OrderItem> orderItems; // Danh sách các món trong đơn hàng
    private LocalDateTime orderDate; // Thời gian lúc tạo đơn
    private double totalAmount; // Tổng tiền của hóa đơn

    public Order(int orderID) {
        this.orderID = orderID;
        this.orderItems = new ArrayList<>();
        this.orderDate = LocalDateTime.now();
        this.totalAmount = 0;
    }

    /* Thêm hàng vào đơn hàng, nếu đơn có thì tăng số lượng */
    public void addItem(Product product, int quantity) {
        // Kiểm tra có hàng chưa
        for(OrderItem item : this.orderItems) {
            if(item.getProduct().getProductID().equals((product.getProductID()))) {
                item.setQuantity(item.getQuantity() + quantity);
                calculateTotalAmount();
                return;
            }
        }
        OrderItem newItem = new OrderItem(product, quantity);
        this.orderItems.add(newItem);
        calculateTotalAmount();
    }
    /* Tính tổng tiền của đơn hàng */
    public void calculateTotalAmount() {
        this.totalAmount = 0;
        for(OrderItem item : this.orderItems) {
            this.totalAmount += item.getTotal();
        }
    }

    // getter
    public int getOrderID() { return this.orderID; }
    public List<OrderItem> getOrderItems() { return this.orderItems; }
    public LocalDateTime getOrderDate() { return this.orderDate; }
    public double getTotalAmount() { return this.totalAmount; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order Details:\n");
        sb.append("Order ID: ").append(orderID).append("\n");
        sb.append("Date: ").append(orderDate).append("\n");
        sb.append("Items:\n");
        for (OrderItem item : orderItems) {
            sb.append("\t- ").append(item.getProduct().getName())
                    .append(" | Quantity: ").append(item.getQuantity())
                    .append(" | Subtotal: ").append(item.getTotal()).append("\n");
        }
        sb.append("----------------------------------\n");
        sb.append("Total Amount: ").append(totalAmount).append("\n");
        return sb.toString();
    }
}
