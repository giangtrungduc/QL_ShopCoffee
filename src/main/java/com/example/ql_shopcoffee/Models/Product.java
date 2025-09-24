package com.example.ql_shopcoffee.Models;

import com.example.ql_shopcoffee.Models.Enums.ProductType;

import java.util.Objects;

public class Product {
    private String id;
    private String name;
    private ProductType type;
    private double price;

    public Product(String id, String name, ProductType type, double price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
    }

    // getter
    public String getId() { return id; }
    public String getName() { return name; }
    public ProductType getType() { return type; }
    public double getPrice() { return price; }

    // setter
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setType(ProductType type) { this.type = type; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
