package com.example.ql_shopcoffee.Models;

import com.example.ql_shopcoffee.Models.Enums.TableStatus;

import java.util.Objects;

public class Table {
    private int tableNumber;
    private TableStatus tableStatus;

    // getter
    public int getTableNumber() { return tableNumber; }
    public TableStatus getTableStatus() { return tableStatus; }

    // setter
    public void setTableNumber(int tableNumber) { this.tableNumber = tableNumber; }
    public void setTableStatus(TableStatus tableStatus) { this.tableStatus = tableStatus; }

    @Override
    public String toString() {
        return "Table{" +
                "tableNumber=" + tableNumber +
                ", tableStatus=" + tableStatus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return tableNumber == table.tableNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tableNumber);
    }
}
