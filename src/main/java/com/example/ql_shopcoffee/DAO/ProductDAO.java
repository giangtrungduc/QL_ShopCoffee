package com.example.ql_shopcoffee.DAO;

import com.example.ql_shopcoffee.DATA.DatabaseConnection;
import com.example.ql_shopcoffee.Models.enums.ProductStatus;
import com.example.ql_shopcoffee.Models.enums.ProductType;
import com.example.ql_shopcoffee.Models.models.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public Product findById(String productId) {
        String sql = "SELECT * FROM products WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, productId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToProduct(rs);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm sản phẩm: " + e.getMessage());
        }
        return null;
    }

    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection conn = DatabaseConnection.getInstance();
             Statement pstmt = conn.createStatement();
             ResultSet rs = pstmt.executeQuery(sql)) {

            while(rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách sản phẩm: " + e.getMessage());
        }
        return products;
    }

    public void save(Product product) {
        String sql = "INSERT INTO products(id, name, type, price, quantity_on_hand, quantity_reserved, status) VALUES(?,?,?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setProductParameters(pstmt, product);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi khi lưu sản phẩm: " + e.getMessage());
        }
    }

    public void update(Product product) {
        String sql = "UPDATE products SET name = ?, type = ?, price = ?, quantity_on_hand = ?, quantity_reserved = ?, status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Gán các tham số giống save nhưng thứ tự khác
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getType().name());
            pstmt.setDouble(3, product.getPrice());
            pstmt.setInt(4, product.getQuantityOnHand());
            pstmt.setInt(5, product.getQuantityReserved());
            pstmt.setString(6, product.getStatus().name());
            pstmt.setString(7, product.getId()); // ID ở cuối
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật sản phẩm: " + e.getMessage());
        }
    }

    /**
     * Cập nhật số lượng tồn kho và tạm giữ.
     * @param productId ID sản phẩm.
     * @param onHandChange Lượng thay đổi trong kho (có thể âm hoặc dương).
     * @param reservedChange Lượng thay đổi đang tạm giữ (có thể âm hoặc dương).
     */
    public void updateStock(String productId, int onHandChange, int reservedChange) {
        String sql = "UPDATE products SET quantity_on_hand = quantity_on_hand + ?, quantity_reserved = quantity_reserved + ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, onHandChange);
            pstmt.setInt(2, reservedChange);
            pstmt.setString(3, productId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật kho: " + e.getMessage());
        }
    }

    // Phương thức private để ánh xạ
    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        return new Product(
                rs.getString("id"),
                rs.getString("name"),
                ProductType.valueOf(rs.getString("type")),
                rs.getDouble("price"),
                rs.getInt("quantity_on_hand"),
                rs.getInt("quantity_reserved"),
                ProductStatus.valueOf(rs.getString("status"))
        );
    }

    // Phương thức private để gán tham số cho PreparedStatement
    private void setProductParameters(PreparedStatement pstmt, Product product) throws SQLException {
        pstmt.setString(1, product.getId());
        pstmt.setString(2, product.getName());
        pstmt.setString(3, product.getType().name());
        pstmt.setDouble(4, product.getPrice());
        pstmt.setInt(5, product.getQuantityOnHand());
        pstmt.setInt(6, product.getQuantityReserved());
        pstmt.setString(7, product.getStatus().name());
    }
}
