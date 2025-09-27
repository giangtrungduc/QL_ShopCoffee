package com.example.ql_shopcoffee.Models.models;

import com.example.ql_shopcoffee.Models.enums.DiscountType;
import com.example.ql_shopcoffee.Models.enums.ProductType;

import java.time.LocalDateTime;

public class Promotion {
    private String id;
    private DiscountType type;
    private double value;
    private ProductType targetProductType;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Promotion() {}

    public Promotion(String id,  DiscountType type, double value, ProductType targetProductType, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.type = type;
        this.value = value;
        this.targetProductType = targetProductType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public DiscountType getType() { return type; }
    public void setType(DiscountType type) { this.type = type; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    public ProductType getTargetProductType() { return targetProductType; }
    public void setTargetProductType(ProductType targetProductType) { this.targetProductType = targetProductType; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }
}
