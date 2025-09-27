package com.example.ql_shopcoffee.Models.models;

import com.example.ql_shopcoffee.Models.enums.ProductStatus;
import com.example.ql_shopcoffee.Models.enums.ProductType;

public class Product {
    private String id; // mã sản phẩm
    private String name; // tên sản phẩm
    private ProductType type; // loại sản phẩm
    private double price; // giá sản phẩm
    private int quantityOnHand; // số lượng đang giữ
    private int quantityReserved; // số lượng đã xóa
    private ProductStatus status; // trạng thái sản phẩm

    public Product() {}
    public Product(String id, String name, ProductType type, double price, int quantityOnHand, int quantityReserved, ProductStatus status) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.quantityOnHand = quantityOnHand;
        this.quantityReserved = quantityReserved;
        this.status = status;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ProductType getType() { return type; }
    public void setType(ProductType type) { this.type = type; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantityOnHand() { return quantityOnHand; }
    public void setQuantityOnHand(int quantityOnHand) { this.quantityOnHand = quantityOnHand; }

    public int getQuantityReserved() { return quantityReserved; }
    public void setQuantityReserved(int quantityReserved) { this.quantityReserved = quantityReserved; }

    public ProductStatus getStatus() { return status; }
    public void setStatus(ProductStatus status) { this.status = status; }
}
