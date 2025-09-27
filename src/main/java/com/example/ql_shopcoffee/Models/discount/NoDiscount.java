package com.example.ql_shopcoffee.Models.discount;

public class NoDiscount implements DiscountStrategy{
    @Override
    public double applyDiscount(double totalAmount) {
        return totalAmount; // Trả về đúng số tiền ban đầu
    }
}
