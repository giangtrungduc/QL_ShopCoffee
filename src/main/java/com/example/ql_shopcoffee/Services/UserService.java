package com.example.ql_shopcoffee.Services;

import com.example.ql_shopcoffee.DAO.UserDAO;
import com.example.ql_shopcoffee.Models.enums.Role;
import com.example.ql_shopcoffee.Models.models.User;

import java.util.List;

public class UserService {
    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    /**
     * Xác thực đăng nhập người dùng.
     * @return Role của người dùng nếu thành công, ngược lại trả về null.
     */
    public Role login(String username, String password) {
        User user = userDAO.findByUsername(username);
        if(user != null) {
            if(user.getPassword().equals(password)) {
                return user.getRole();
            }
        }
        return null;
    }

    /**
     * Thêm người dùng mới (chỉ ADMIN được làm).
     * @return true nếu thành công, false nếu người dùng đã tồn tại.
     */
    public boolean addUser(String username, String password, String fullName, Role role) {
        if(userDAO.findByUsername(username) != null) {
            System.err.println("Lỗi: Tên đăng nhập '" + username + "' đã tồn tại.");
            return false;
        }

        User user = new User(username, password, fullName, role);
        userDAO.save(user);
        return true;
    }

    /**
     * Lấy danh sách tất cả người dùng.
     */
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }
}
