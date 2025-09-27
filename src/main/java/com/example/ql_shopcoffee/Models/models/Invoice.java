package com.example.ql_shopcoffee.Models.models;


import com.example.ql_shopcoffee.Models.enums.InvoiceStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Invoice {
    private String id;
    private User createBy;
    private Table table;
    private LocalDateTime createAt;
    private InvoiceStatus status;
    private double subTotal;
    private double finalTotal;
    private List<OrderItem> items;

    public Invoice() {
        this.items = new ArrayList<>();
    }

    public Invoice(String id, User createBy, Table table, LocalDateTime createAt, InvoiceStatus status) {
        this.id = id;
        this.createBy = createBy;
        this.table = table;
        this.createAt = createAt;
        this.status = status;
        this.items = new ArrayList<>();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public User getCreateBy() { return createBy; }
    public void setCreateBy(User createBy) { this.createBy = createBy; }

    public Table getTable() { return table; }
    public void setTable(Table table) { this.table = table; }

    public LocalDateTime getCreateAt() { return createAt; }
    public void setCreateAt(LocalDateTime createAt) { this.createAt = createAt; }

    public InvoiceStatus getStatus() { return status; }
    public void setStatus(InvoiceStatus status) { this.status = status; }

    public double getSubTotal() { return subTotal; }
    public void setSubTotal(double subTotal) { this.subTotal = subTotal; }

    public double getFinalTotal() { return finalTotal; }
    public void setFinalTotal(double finalTotal) { this.finalTotal = finalTotal; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
}
