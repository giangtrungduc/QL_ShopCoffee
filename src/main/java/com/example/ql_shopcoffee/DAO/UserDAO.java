package com.example.ql_shopcoffee.DAO;

import com.example.ql_shopcoffee.DATA.DatabaseConnection;
import com.example.ql_shopcoffee.Models.enums.Role;
import com.example.ql_shopcoffee.Models.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    /**
     * Tìm kiếm User theo username.
     * @param username Tên đăng nhập cần tìm.
     * @return Đối tượng User nếu tìm thấy, ngược lại trả về null.
     */
    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try(Connection conn = DatabaseConnection.getInstance();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs =  pstmt.executeQuery();

            if(rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm User: " + e.getMessage());
        }
        return null;
    }

    /**
     * Lấy danh sách tất cả User.
     * @return List các đối tượng User.
     */
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try(Connection conn = DatabaseConnection.getInstance();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {

            while(rs.next()) {
                users.add(mapResultSetToUser(rs));
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách user: " + e.getMessage());
        }
        return users;
    }

    /**
     * Lưu một User mới vào CSDL.
     * @param user Đối tượng User cần lưu.
     */
    public void save(User user) {
        String sql = "INSERT INTO users(username, password, full_name, role) VALUES(?, ?, ?, ?)";
        try(Connection conn = DatabaseConnection.getInstance();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getFullName());
            pstmt.setString(4, user.getRole().name());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi khi lưu user: " + e.getMessage());
        }
    }

    // Phương thức private để ánh xạ ResultSet sang đối tượng User
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("full_name"),
                Role.valueOf(rs.getString("role"))
        );
    }
}
