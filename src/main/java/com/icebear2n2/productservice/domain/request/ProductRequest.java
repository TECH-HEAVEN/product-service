package com.icebear2n2.productservice.domain.request;

import com.icebear2n2.productservice.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String categoryName;
    private String productName;
    private Integer productPrice;

    public Product toEntity() {
        return Product.builder()
                .productName(productName)
                .productPrice(productPrice)
                .build();
    }
}
