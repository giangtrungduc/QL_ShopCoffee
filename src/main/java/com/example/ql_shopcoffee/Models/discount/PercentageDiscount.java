package com.example.ql_shopcoffee.Models.discount;

public class PercentageDiscount implements DiscountStrategy{
    private final double percentage;

    public PercentageDiscount(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public double applyDiscount(double totalAmount) {
        if (percentage < 0 || percentage > 100) {
            return totalAmount; // Không giảm giá nếu phần trăm không hợp lệ
        }
        return totalAmount * (1 - percentage / 100.0);
    }
}
