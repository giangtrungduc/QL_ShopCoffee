package com.example.ql_shopcoffee.Models;

import com.example.ql_shopcoffee.Models.Enums.InvoiceStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Invoice {
    private String id;
    private List<Product> items;
    private LocalDateTime creatAt;
    private InvoiceStatus status;
    private double subTotal;
    private double finalAmount;
    private Table table;

    // getter
    public String getId() { return id; }
    public List<Product> getItems() { return items; }
    public LocalDateTime getCreatAt() { return creatAt; }
    public InvoiceStatus getStatus() { return status; }
    public double getSubTotal() { return subTotal; }
    public double getFinalAmount() { return finalAmount; }
    public Table getTable() { return table; }

    // setter
    public void setId(String id) { this.id = id; }
    public void setItems(List<Product> items) { this.items = items; }
    public void setCreatAt(LocalDateTime creatAt) { this.creatAt = creatAt; }
    public void setStatus(InvoiceStatus status) { this.status = status; }
    public void setSubTotal(double subTotal) { this.subTotal = subTotal; }
    public void setFinalAmount(double finalAmount) { this.finalAmount = finalAmount; }
    public void setTable(Table table) { this.table = table; }

    @Override
    public String toString() {
        return "Invoice{" +
                "id='" + id + '\'' +
                ", items=" + items +
                ", creatAt=" + creatAt +
                ", status=" + status +
                ", subTotal=" + subTotal +
                ", finalAmount=" + finalAmount +
                ", table=" + table +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(id, invoice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
