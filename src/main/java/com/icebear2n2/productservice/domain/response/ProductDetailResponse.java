package com.icebear2n2.productservice.domain.response;

import com.icebear2n2.productservice.domain.entity.Product;
import com.icebear2n2.productservice.domain.entity.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailResponse {
    private boolean success;
    private String message;
    private ProductDetailData productDetailData;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductDetailData {
        private Product product;
        private List<String> productColors;
        private List<String> productSizes;
        private List<Integer> stockQuantity;
        private String createdAt;
        private String updatedAt;

        public ProductDetailData(ProductDetail productDetail) {
            this.product = productDetail.getProduct();
            this.productColors = productDetail.getProductColors() != null ? productDetail.getProductColors() : null;
            this.productSizes = productDetail.getProductSizes() != null ? productDetail.getProductSizes() : null;
            this.stockQuantity = productDetail.getStockQuantity();
            this.createdAt = productDetail.getCreatedAt().toString();
            this.updatedAt = productDetail.getUpdatedAt().toString();
        }
    }

    public static ProductDetailResponse success(ProductDetail productDetail) {
        return new ProductDetailResponse(true, "Success", new ProductDetailData(productDetail));
    }
    public static ProductDetailResponse failure(String message) {
        return new ProductDetailResponse(false, message, null);
    }
}
