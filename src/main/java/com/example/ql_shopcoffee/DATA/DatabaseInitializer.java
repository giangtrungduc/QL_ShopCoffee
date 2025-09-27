package com.example.ql_shopcoffee.DATA;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Chịu trách nhiệm khởi tạo cấu trúc CSDL (tạo các bảng).
 */
public class DatabaseInitializer {

    public static void initializeDatabase() {
        // Lấy kết nối từ lớp Singleton
        Connection conn = DatabaseConnection.getInstance();
        if(conn != null) {
            System.err.println("Không thể khởi tạo CSDL vì kết nối null");
            return;
        }

        // Dùng try-with-resources để Statement tự động được đóng
        try(Statement stmt = conn.createStatement()) {

            // Bật ràng buộc khóa ngoại cho SQLite
            stmt.execute("PRAGMA foreign_keys = ON;");

            // Câu lệnh tạo bảng 'users'
            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "username TEXT PRIMARY KEY," +
                    "password TEXT NOT NULL," +
                    "full_name TEXT NOT NULL," +
                    "role TEXT NOT NULL CHECK(role IN ('ADMIN', 'STAFF'))" +
                    ");");

            // Câu lệnh tạo bảng 'products'
            stmt.execute("CREATE TABLE IF NOT EXISTS products (" +
                    "id TEXT PRIMARY KEY," +
                    "name TEXT NOT NULL," +
                    "type TEXT NOT NULL," +
                    "price REAL NOT NULL," +
                    "quantity_on_hand INTEGER NOT NULL," +
                    "quantity_reserved INTEGER NOT NULL," +
                    "status TEXT NOT NULL CHECK(status IN ('AVAILABLE', 'OUT_OF_STOCK'))" +
                    ");");

            // Câu lệnh tạo bảng 'tables'
            stmt.execute("CREATE TABLE IF NOT EXISTS tables (" +
                    "table_number INTEGER PRIMARY KEY," +
                    "status TEXT NOT NULL CHECK(status IN ('AVAILABLE', 'OCCUPIED'))" +
                    ");");

            // Câu lệnh tạo bảng 'invoices'
            stmt.execute("CREATE TABLE IF NOT EXISTS invoices (" +
                    "id TEXT PRIMARY KEY," +
                    "created_by_username TEXT NOT NULL," +
                    "table_number INTEGER NOT NULL," +
                    "created_at TEXT NOT NULL," +
                    "status TEXT NOT NULL CHECK(status IN ('PENDING', 'PAID', 'CANCELLED'))," +
                    "sub_total REAL," +
                    "final_total REAL," +
                    "FOREIGN KEY (created_by_username) REFERENCES users(full_name)," +
                    "FOREIGN KEY (table_number) REFERENCES tables(table_number)" +
                    ");");

            // Câu lệnh tạo bảng 'order_items'
            stmt.execute("CREATE TABLE IF NOT EXISTS order_items (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "invoice_id TEXT NOT NULL," +
                    "product_id TEXT NOT NULL," +
                    "quantity INTEGER NOT NULL," +
                    "price_at_order REAL NOT NULL," +
                    "FOREIGN KEY (invoice_id) REFERENCES invoices(id)," +
                    "FOREIGN KEY (product_id) REFERENCES products(id)" +
                    ");");

            // Câu lệnh tạo bảng 'promotions'
            stmt.execute("CREATE TABLE IF NOT EXISTS promotions (" +
                    "id TEXT PRIMARY KEY," +
                    "type TEXT NOT NULL CHECK(type IN ('PERCENTAGE', 'FIXED_AMOUNT'))," +
                    "value REAL NOT NULL," +
                    "target_product_type TEXT," +
                    "start_date TEXT NOT NULL," +
                    "end_date TEXT NOT NULL" +
                    ");");

            System.out.println("Khởi tạo CSDL và các bảng thành công (nếu chưa tồn tại).");
        } catch (SQLException e) {
            System.err.println("Lỗi khi tạo bảng: " + e.getMessage());
        }
    }
}
