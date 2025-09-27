package com.example.ql_shopcoffee.Models.models;

public class OrderItem {
    private int id;
    private Product product;
    private int quantity;
    private double priceAtOrder;

    public OrderItem() {}
    public OrderItem(int id, Product product, int quantity, double priceAtOrder) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.priceAtOrder = priceAtOrder;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPriceAtOrder() { return priceAtOrder; }
    public void setPriceAtOrder(double priceAtOrder) { this.priceAtOrder = priceAtOrder; }
}
