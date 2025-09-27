package com.example.ql_shopcoffee.DAO;

import com.example.ql_shopcoffee.DATA.DatabaseConnection;
import com.example.ql_shopcoffee.Models.enums.InvoiceStatus;
import com.example.ql_shopcoffee.Models.models.Invoice;
import com.example.ql_shopcoffee.Models.models.Table;
import com.example.ql_shopcoffee.Models.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public Invoice findById(String invoiceId) {
        String sql = "SELECT * FROM invoices WHERE id = ?";
        try(Connection conn = DatabaseConnection.getInstance();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, invoiceId);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                return mapResultSetToInvoice(rs);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm hóa đơn: " + e.getMessage());
        }
        return null;
    }

    public List<Invoice> findAll() {
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT * FROM invoices ORDER BY created_at DESC";
        try(Connection conn = DatabaseConnection.getInstance();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                invoices.add(mapResultSetToInvoice(rs));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách hóa đơn: " + e.getMessage());
        }
        return invoices;
    }

    public Invoice findPendingInvoiceByTable(int tableNumber) {
        String sql = "SELECT * FROM invoices WHERE table_number = ? AND status = 'PENDING'";
        try(Connection conn = DatabaseConnection.getInstance();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, tableNumber);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                return mapResultSetToInvoice(rs);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm hóa đơn PENDING của bàn: " + e.getMessage());
        }
        return null;
    }

    public void save(Invoice invoice) {
        String sql = "INSERT INTO invoices(id, created_by_username, table_number, created_at, status, sub_total, final_total) VALUES(?,?,?,?,?,?,?)";
        try(Connection conn = DatabaseConnection.getInstance();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, invoice.getId());
            pstmt.setString(2, invoice.getCreateBy().getFullName());
            pstmt.setInt(3, invoice.getTable().getTableNumber());
            pstmt.setString(4, invoice.getCreateAt().format(formatter));
            pstmt.setString(5, invoice.getStatus().name());
            pstmt.setDouble(6, invoice.getSubTotal());
            pstmt.setDouble(7, invoice.getFinalTotal());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi khi lưu hóa đơn: " + e.getMessage());
        }
    }

    public void update(Invoice invoice) {
        String sql = "UPDATE invoices SET status = ?, sub_total = ?, final_total = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, invoice.getStatus().name());
            pstmt.setDouble(2, invoice.getSubTotal());
            pstmt.setDouble(3, invoice.getFinalTotal());
            pstmt.setString(4, invoice.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật hóa đơn: " + e.getMessage());
        }
    }

    private Invoice mapResultSetToInvoice(ResultSet rs) throws SQLException {
        Invoice invoice = new Invoice();
        invoice.setId(rs.getString("id"));

        // Tạo đối tượng User và Table tạm thời chỉ với ID
        User user = new User();
        user.setUsername(rs.getString("created_by_username"));
        invoice.setCreateBy(user);

        Table table = new Table();
        table.setTableNumber(rs.getInt("table_number"));
        invoice.setTable(table);

        invoice.setCreateAt(LocalDateTime.parse(rs.getString("created_at"), formatter));
        invoice.setStatus(InvoiceStatus.valueOf(rs.getString("status")));
        invoice.setSubTotal(rs.getDouble("sub_total"));
        invoice.setFinalTotal(rs.getDouble("final_total"));
        return invoice;
    }
}
