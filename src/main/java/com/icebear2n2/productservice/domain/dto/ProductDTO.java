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
public class ProductDTO {
    private Long productId;
    private CategoryDTO category;
    private String productName;
    private Integer productPrice;
    private Integer discountPrice;
    private Timestamp saleStartDate;
    private Timestamp saleEndDate;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private List<ProductDetailDTO> productDetails;
}