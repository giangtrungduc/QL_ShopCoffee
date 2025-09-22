package com.example.ql_shopcoffee.Models;

/* Lớp OrderItem đại diện cho một mục trong hóa đơn*/
public class OrderItem {
    private Product product;
    private int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    /* Tính thành tiền cho mục này */
    public double getTotal(){
        return product.getPrice() * quantity;
    }

    /* getter & setter cho product */
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    /* getter & setter cho quantity */
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return "OrderItem{" + "product=" + product.getName() + ", quantity=" + quantity + ", subtotal=" + getTotal() + '}';
    }
}
