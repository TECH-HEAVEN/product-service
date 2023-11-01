package com.icebear2n2.productservice.domain.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailDTO {
    private Long productDetailId;
    private Long productId;  // Optional: Include this if you need to reference the parent product
    private List<String> productColors;
    private List<String> productSizes;
    private List<Integer> stockQuantity;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}