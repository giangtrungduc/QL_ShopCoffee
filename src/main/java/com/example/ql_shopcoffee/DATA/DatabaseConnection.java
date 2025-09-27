package com.example.ql_shopcoffee.DATA;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Quản lý kết nối CSDL duy nhất cho toàn bộ ứng dụng.
 */
public class DatabaseConnection {

    // URL kết nối đến file SQLite.
    private  static final String URL = "jdbc:sqlite:coffeeshop.db";

    // Biến static để giữ kết nối duy nhất
    private static volatile Connection instance = null;

    // Constructor private để ngăn việc tạo đối tượng từ bên ngoài
    private DatabaseConnection() {}

    /**
     * Phương thức public để lấy thể hiện (instance) duy nhất của kết nối.
     * Triển khai double-checked locking để đảm bảo thread-safe.
     *
     * @return Đối tượng Connection duy nhất.
     */
    public static Connection getInstance() {
        if(instance == null) {
            synchronized (DatabaseConnection.class) {
                if(instance == null) {
                    try {
                        instance = DriverManager.getConnection(URL);
                        System.out.println("Kết nối tới SQLite được thiết lập.");
                    } catch (SQLException e) {
                        System.err.println("Lỗi kết nối: " + e.getMessage());
                    }
                }
            }
        }
        return instance;
    }

    /**
     * Phương thức để đóng kết nối khi ứng dụng kết thúc.
     */
    public static void closeConnection() {
        if(instance != null) {
            try {
                instance.close();
                System.out.println("Kết nối tới SQLite đã được đóng.");
            } catch (SQLException e) {
                System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
            }
        }
    }
}
