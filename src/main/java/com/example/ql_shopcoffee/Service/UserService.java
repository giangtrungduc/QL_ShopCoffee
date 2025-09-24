package com.example.ql_shopcoffee.Service;

import com.example.ql_shopcoffee.Models.Enums.Role;
import com.example.ql_shopcoffee.Models.User;

public class UserService {
    /**
     * * @param username Tên đăng nhập do người dùng nhập vào.
     * * @param password Mật khẩu do người dùng nhập vào.
     * * @return Đối tượng User nếu đăng nhập thành công, ngược lại trả về null.
     */
    public User login(String username, String password) {

        if(username == null || username.trim().isEmpty()) {
            System.out.println("Không để được để trống username");
            return null;
        }
        if(password == null || password.trim().isEmpty()) {
            System.out.println("Không để trống password");
            return null;
        }

        // Nếu có dữ liệu lưu từ trước
        //User userFromDataSource = userDAO.findByUsername(username);

        // Giả lập vì chưa có dữ liệu cơ sở
        User userFromDataSource = null;
        if ("admin".equals(username)) {
            userFromDataSource = new User("admin", "admin123", Role.ADMIN);
        } else if ("staff".equals(username)) {
            userFromDataSource = new User("staff", "staff123", Role.STAFF);
        }

        if(userFromDataSource != null) {
            if(userFromDataSource.getPassword().equals(password)) {
                System.out.println("Đăng nhập thành công");
                return userFromDataSource;
            } else {
                System.out.println("Lỗi: Sai mật khẩu");
                return null;
            }
        } else {
            System.out.println("Lỗi: Tên đăng nhập không tồn tại");
        }
        return null;
    }
}
