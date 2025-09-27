package com.example.ql_shopcoffee.Models.models;

import com.example.ql_shopcoffee.Models.enums.Role;


// Khai báo người dùng
public class User {
    private String username; // tên đăng nhập
    private String password; // mật khẩu
    private String fullName; // tên đầy đủ
    private Role role; // chức vụ

    public User() {}
    public User(String username, String password, String fullName, Role role) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
    }

    // Getters and Setters
    public String getUsername() { return this.username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return this.password; }
    public void setPassword(String password) { this.password = password; }

    public String getFullName() { return this.fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public Role getRole() { return this.role; }
    public void setRole(Role role) { this.role = role; }
}
