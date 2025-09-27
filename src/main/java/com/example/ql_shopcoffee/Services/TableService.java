package com.example.ql_shopcoffee.Services;

import com.example.ql_shopcoffee.DAO.TableDAO;
import com.example.ql_shopcoffee.Models.models.Table;

import java.util.List;

public class TableService {
    private final TableDAO tableDAO;

    public TableService() {
        this.tableDAO = new TableDAO();
    }

    /**
     * Lấy trạng thái của tất cả các bàn.
     */
    public List<Table> getAllTablesStatus() {
        return tableDAO.findAll();
    }
}
