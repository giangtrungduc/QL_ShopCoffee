package com.example.ql_shopcoffee.Service;

public interface DiscountStrategy {
    double applyDiscount(double totalAmount);
}

// Giảm giá theo phần trăm
class PercentageDiscount implements DiscountStrategy {
    private double percentage;
    public PercentageDiscount(double percentage) {
        this.percentage = percentage;
    }
    @Override
    public double applyDiscount(double totalAmount) {
        return totalAmount * (1 - percentage / 100);
    }
}

class FixedAmountDiscount implements DiscountStrategy {
    private double amount;
    public FixedAmountDiscount(double amount) {
        this.amount = amount;
    }
    @Override
    public double applyDiscount(double totalAmount) {
        return Math.max(0, totalAmount - amount);
    }
}
