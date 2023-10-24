package com.icebear2n2.productservice.domain.request;
import com.icebear2n2.productservice.domain.entity.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailRequest {
    private String  productName;
    private List<String> productColors;
    private List<String> productSizes;
    private List<Integer> stockQuantity;

    public ProductDetail toEntity() {
        return ProductDetail.builder()
                .productColors(productColors)
                .productSizes(productSizes)
                .stockQuantity(stockQuantity)
                .build();
    }
}
