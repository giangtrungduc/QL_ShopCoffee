package com.example.ql_shopcoffee.Models;

public class Table {
    private int tableNumber;
    private String status; // "Trống", "Có khách"

    public Table(int tableNumber, String status) {
        this.tableNumber = tableNumber;
        this.status = status;
    }

    // getter & setter cho số bàn
    public int getTableNumber() {
        return tableNumber;
    }
    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    // getter & setter cho trạng thái
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Table{" + "tableNumber=" + tableNumber + ", status='" + status + '\'' + '}';
    }
}