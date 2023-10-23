package com.icebear2n2.productservice.domain.request;

import com.icebear2n2.productservice.domain.entity.Product;
import com.icebear2n2.productservice.domain.entity.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDetailRequest {
    private Product product;
    private String productColor;
    private String productSize;
    private Integer stockQuantity;

    public ProductDetail toEntity() {
        return ProductDetail.builder()
                .product(product)
                .productColor(productColor)
                .productSize(productSize)
                .stockQuantity(stockQuantity)
                .build();
    }
}
