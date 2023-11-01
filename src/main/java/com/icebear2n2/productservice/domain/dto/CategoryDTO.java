package com.icebear2n2.productservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private Long categoryId;
    private String categoryName;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
