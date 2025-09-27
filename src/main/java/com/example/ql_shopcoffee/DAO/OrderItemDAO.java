package com.example.ql_shopcoffee.DAO;

import com.example.ql_shopcoffee.DATA.DatabaseConnection;
import com.example.ql_shopcoffee.Models.models.OrderItem;
import com.example.ql_shopcoffee.Models.models.Product;
import com.example.ql_shopcoffee.Models.models.Promotion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO {
    public List<OrderItem> findByInvoiceId(String invoiceId) {
        List<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT * FROM order_items WHERE invoice_id = ?";
        try(Connection conn = DatabaseConnection.getInstance();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, invoiceId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                orderItems.add(mapResultSetToOrderItem(rs));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm các món trong hóa đơn: " + e.getMessage());
        }
        return orderItems;
    }

    public OrderItem findById(int orderItemId) {
        String sql = "SELECT * FROM order_items WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderItemId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToOrderItem(rs);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm OrderItem theo ID: " + e.getMessage());
        }
        return null;
    }

    public void save(OrderItem item, String invoiceId) {
        String sql = "INSERT INTO order_items(invoice_id, product_id, quantity, price_at_order) VALUES(?,?,?,?)";
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, invoiceId);
            pstmt.setString(2, item.getProduct().getId());
            pstmt.setInt(3, item.getQuantity());
            pstmt.setDouble(4, item.getPriceAtOrder());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi khi lưu món: " + e.getMessage());
        }
    }

    public void update(OrderItem item) {
        String sql = "UPDATE order_items SET quantity = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, item.getQuantity());
            pstmt.setInt(2, item.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật món: " + e.getMessage());
        }
    }

    public void delete(int orderItemId) {
        String sql = "DELETE FROM order_items WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderItemId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa món: " + e.getMessage());
        }
    }

    private OrderItem mapResultSetToOrderItem(ResultSet rs) throws SQLException {
        OrderItem item = new OrderItem();
        item.setId(rs.getInt("id"));

        Product product = new Product();
        product.setId(rs.getString("product_id"));

        item.setProduct(product);
        item.setQuantity(rs.getInt("quantity"));
        item.setPriceAtOrder(rs.getDouble("price_at_order"));
        return item;
    }
}
