package com.example.ql_shopcoffee.DAO;

import com.example.ql_shopcoffee.DATA.DatabaseConnection;
import com.example.ql_shopcoffee.Models.enums.TableStatus;
import com.example.ql_shopcoffee.Models.models.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TableDAO {

    public Table findByNumber(int tableNumber) {
        String sql = "SELECT * FROM tables WHERE table_number = ?";
        try(Connection conn = DatabaseConnection.getInstance();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, tableNumber);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                return mapResultSetToTable(rs);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm bàn: " + e.getMessage());
        }
        return null;
    }

    public List<Table> findAll() {
        List<Table> tables = new ArrayList<>();
        String sql = "SELECT * FROM tables ORDER BY table_number ASC";
        try (Connection conn = DatabaseConnection.getInstance();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                tables.add(mapResultSetToTable(rs));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách bàn: " + e.getMessage());
        }
        return tables;
    }

    public void updateStatus(int tableNumber, TableStatus status) {
        String sql = "UPDATE tables SET status = ? WHERE table_number = ?";
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status.name());
            pstmt.setInt(2, tableNumber);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật trạng thái bàn: " + e.getMessage());
        }
    }

    private Table mapResultSetToTable(ResultSet rs) throws SQLException {
        return new Table(
                rs.getInt("table_number"),
                TableStatus.valueOf(rs.getString("status"))
        );
    }
}

