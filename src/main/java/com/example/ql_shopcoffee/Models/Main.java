package com.example.ql_shopcoffee.Models;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Starting Coffee Shop Logic Test ---");

        // 1. Tạo một vài sản phẩm (menu)
        Product coffee = new Product("CF001", "Cà phê Đen", 25000, "Cà phê");
        Product milkTea = new Product("MT001", "Trà sữa Trân châu", 35000, "Trà sữa");
        Product cake = new Product("CK001", "Bánh Tiramisu", 40000, "Bánh ngọt");

        System.out.println("\nAvailable Products:");
        System.out.println(coffee);
        System.out.println(milkTea);
        System.out.println(cake);

        // 2. Tạo một đơn hàng mới
        Order order1 = new Order(101); // Order ID là 101
        System.out.println("\nCreated new order with ID: " + order1.getOrderID());

        // 3. Thêm các sản phẩm vào đơn hàng
        System.out.println("\nAdding items to the order...");
        order1.addItem(coffee, 2); // Thêm 2 ly cà phê đen
        order1.addItem(milkTea, 1); // Thêm 1 ly trà sữa
        order1.addItem(cake, 1); // Thêm 1 bánh Tiramisu

        // In ra chi tiết đơn hàng để kiểm tra
        System.out.println(order1);

        // 4. Thêm sản phẩm đã có để kiểm tra logic tăng số lượng
        System.out.println("Adding an existing item (coffee) again...");
        order1.addItem(coffee, 1); // Thêm 1 ly cà phê đen nữa

        // In lại chi tiết đơn hàng
        System.out.println("\nUpdated Order Details:");
        System.out.println(order1);

        System.out.println("--- Test Completed ---");
    }
}
