package com.example.ql_shopcoffee.Models;

/* Lớp User đại diện cho người dùng */
public class User {
    private String username;
    private String password;
    private String role; // Admin hoặc Staff

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // setter & getter cho username
    public String getUsername() { return this.username; }
    public void setUsername(String username) { this.username = username; }

    // setter & getter cho password
    public String getPassword() { return this.password; }
    public void setPassword(String password) { this.password = password; }

    // setter & getter cho role
    public String getRole() { return this.role; }
    public void setRole(String role) { this.role = role; }

    @Override
    public String toString() {
        return "User{" + "username=" + username + ", password=" + password + ", role=" + role + '}';
    }
}
