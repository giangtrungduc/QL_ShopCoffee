package com.example.ql_shopcoffee.DAO;

import com.example.ql_shopcoffee.DATA.DatabaseConnection;
import com.example.ql_shopcoffee.Models.enums.DiscountType;
import com.example.ql_shopcoffee.Models.enums.ProductType;
import com.example.ql_shopcoffee.Models.models.Promotion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PromotionDAO {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public Promotion findById(String promotionId) {
        String sql = "SELECT * FROM promotions WHERE id = ?";
        try(Connection conn = DatabaseConnection.getInstance();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, promotionId);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                return mapResultSetToPromotion(rs);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm khuyến mãi: " + e.getMessage());
        }
        return null;
    }

    /**
     * Tìm các khuyến mãi đang có hiệu lực cho một loại sản phẩm.
     * @param productType Loại sản phẩm
     * @return Danh sách các khuyến mãi hợp lệ.
     */
    public List<Promotion> findActivePromotionsForProductType(ProductType productType) {
        List<Promotion> promotions = new ArrayList<>();
        String sql = "SELECT * FROM promotions WHERE target_product_type = ? AND start_date <= ? AND end_date >= ?";
        try(Connection conn = DatabaseConnection.getInstance();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String now = LocalDateTime.now().format(formatter);
            pstmt.setString(1, productType.name());
            pstmt.setString(2, now);
            pstmt.setString(3, now);

            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                promotions.add(mapResultSetToPromotion(rs));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm khuyến mãi đang hoạt động: " + e.getMessage());
        }
        return promotions;
    }

    private Promotion mapResultSetToPromotion(ResultSet rs) throws SQLException {
        return new Promotion(
                rs.getString("id"),
                DiscountType.valueOf(rs.getString("type")),
                rs.getDouble("value"),
                ProductType.valueOf(rs.getString("target_product_type")),
                LocalDateTime.parse(rs.getString("start_date"), formatter),
                LocalDateTime.parse(rs.getString("end_date"), formatter)
        );
    }
}
