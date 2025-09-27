package com.example.ql_shopcoffee.Services;

import com.example.ql_shopcoffee.DAO.ProductDAO;
import com.example.ql_shopcoffee.DAO.PromotionDAO;
import com.example.ql_shopcoffee.Models.enums.ProductStatus;
import com.example.ql_shopcoffee.Models.enums.ProductType;
import com.example.ql_shopcoffee.Models.models.Product;

import java.util.List;

public class ProductService {
    private final ProductDAO productDAO;
    private final PromotionService promotionService;

    public ProductService() {
        this.productDAO = new ProductDAO();
        this.promotionService = new PromotionService();
    }

    /**
     * Lấy danh sách menu, đã bao gồm giá sau khuyến mãi và số lượng có thể bán.
     * Đây là một ví dụ về việc trả về một DTO (Data Transfer Object) thay vì đối tượng gốc.
     * Để đơn giản, ta sẽ tính toán và trả về chính đối tượng Product đã được làm giàu thông tin.
     */
    public List<Product> getMenu() {
        List<Product> products = productDAO.findAll();

        // Làm giàu thông tin cho từng sản phẩm
        for(Product product : products) {
            // Giá sau khuyến mãi không được lưu vào CSDL, chỉ tính toán để hiển thị
            double discountedPrice = promotionService.calculateDiscountedPrice(product);
        }
        return products;
    }

    public void addNewProduct(String id, String name, ProductType type, double price, int quantity) {
        Product product = new Product(id, name, type, price, quantity, 0, ProductStatus.AVAILABLE);
        productDAO.save(product);
    }

    public void updateProductInfo(String id, String name, ProductType type, double price) {
        Product product = productDAO.findById(id);
        if(product != null) {
            product.setName(name);
            product.setType(type);
            product.setPrice(price);
            productDAO.update(product);
        }
    }
}
