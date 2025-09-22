package com.example.ql_shopcoffee.Models;
/* Lớp Product đại diện cho 1 sản phẩm */
public class Product {
    private String productID; // Mã sản phẩm
    private String name; // Tên sản phẩm
    private String category; // Loại sản phẩm
    private double price; // Giá sản phẩm

    public Product(String productID, String name, int price, String category) {
        this.productID = productID;
        this.name = name;
        this.category = category;
        this.price = price;
    }
    public String getProductID() { return productID; }
    public void setProductID(String productID) { this.productID = productID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return "Product{" + "productId='" + this.productID + '\'' + ", name='" + this.name + '\'' + ", price=" + this.price + ", category='" + this.category + '\'' + '}';
    }
}
