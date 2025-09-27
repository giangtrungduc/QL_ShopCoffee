package com.example.ql_shopcoffee.Models.models;

import com.example.ql_shopcoffee.Models.enums.TableStatus;

public class Table {
    private int tableNumber; // số bàn
    private TableStatus tableStatus; // trạng thái bàn

    public Table() {}
    public Table(int tableNumber, TableStatus tableStatus) {
        this.tableNumber = tableNumber;
        this.tableStatus = tableStatus;
    }

    // Getters and Setters
    public int getTableNumber() { return tableNumber; }
    public void setTableNumber(int tableNumber) { this.tableNumber = tableNumber; }

    public TableStatus getTableStatus() { return tableStatus; }
    public void setTableStatus(TableStatus status) { this.tableStatus = status; }
}
