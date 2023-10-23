package com.icebear2n2.productservice.domain.response;

import com.icebear2n2.productservice.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private boolean success;
    private String message;
    private ProductData data;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductData {
        private Long productId;
        private String categoryName;
        private String productName;
        private Integer productPrice;
        private Integer discountPrice;
        private String saleStartDate;
        private String saleEndDate;
        private String createdAt;
        private String updatedAt;

        public ProductData(Product product) {
            this.productId = product.getProductId();
            this.categoryName = product.getCategory().getCategoryName();
            this.productName = product.getProductName();
            this.productPrice = product.getProductPrice();
            this.discountPrice = product.getDiscountPrice();
            this.saleStartDate = product.getSaleStartDate() != null ? product.getSaleStartDate().toString() : null;
            this.saleEndDate = product.getSaleEndDate() != null ? product.getSaleEndDate().toString() : null;
            this.createdAt = product.getCreatedAt().toString();
            this.updatedAt = product.getUpdatedAt().toString();
        }
    }

    public static ProductResponse success(Product product) {
        return new ProductResponse(true, "Success", new ProductData(product));
    }

    public static ProductResponse failure(String message) {
        return new ProductResponse(false, message, null);
    }
}