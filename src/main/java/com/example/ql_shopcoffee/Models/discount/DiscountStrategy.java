package com.example.ql_shopcoffee.Models.discount;

public interface DiscountStrategy {
    /**
     * Áp dụng logic giảm giá vào một tổng tiền cho trước.
     * @param totalAmount Tổng tiền ban đầu.
     * @return Tổng tiền sau khi đã áp dụng giảm giá.
     */
    double applyDiscount(double totalAmount);
}
