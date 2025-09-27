package com.example.ql_shopcoffee.Models.discount;

public class FixedAmountDiscount implements DiscountStrategy{
    private final double amount;

    public FixedAmountDiscount(double amount) {
        this.amount = amount;
    }

    @Override
    public double applyDiscount(double totalAmount) {
        return Math.max(0, totalAmount - amount); // Đảm bảo tổng tiền không bị âm
    }
}
