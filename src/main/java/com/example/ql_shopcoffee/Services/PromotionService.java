package com.example.ql_shopcoffee.Services;

import com.example.ql_shopcoffee.DAO.PromotionDAO;
import com.example.ql_shopcoffee.Models.enums.DiscountType;
import com.example.ql_shopcoffee.Models.models.Product;
import com.example.ql_shopcoffee.Models.models.Promotion;

import java.util.List;

public class PromotionService {
    private final PromotionDAO promotionDAO;

    public PromotionService() {
        this.promotionDAO = new PromotionDAO();
    }

    /**
     * Tìm khuyến mãi tốt nhất (giảm giá nhiều nhất) và còn hiệu lực cho một sản phẩm.
     * @return Đối tượng Promotion hoặc null nếu không có.
     */
    public Promotion getApplicablePromotion(Product product) {
        List<Promotion> promotions = promotionDAO.findActivePromotionsForProductType(product.getType());

        if(promotions.isEmpty()) {
            return null;
        }

        Promotion bestPromotion = null;
        double bestDiscount = 0;

        for(Promotion promotion : promotions) {
            double currentDiscount = 0;
            if(promotion.getType() == DiscountType.PERCENTAGE) {
                currentDiscount = product.getPrice() * (promotion.getValue() / 100.0);
            } else if (promotion.getType() == DiscountType.FIXED_AMOUNT) {
                currentDiscount = promotion.getValue();
            }

            if (currentDiscount > bestDiscount) {
                bestDiscount = currentDiscount;
                bestPromotion = promotion;
            }
        }
        return bestPromotion;
    }

    /**
     * Tính giá cuối cùng của sản phẩm sau khi đã áp dụng khuyến mãi tốt nhất.
     */
    public double calculateDiscountedPrice(Product product) {
        Promotion promotion = getApplicablePromotion(product);
        if (promotion == null) {
            return product.getPrice();
        }

        if (promotion.getType() == DiscountType.PERCENTAGE) {
            return product.getPrice() * (1 - promotion.getValue() / 100.0);
        } else if (promotion.getType() == DiscountType.FIXED_AMOUNT) {
            return Math.max(0, product.getPrice() - promotion.getValue());
        }
        return product.getPrice();
    }
}
