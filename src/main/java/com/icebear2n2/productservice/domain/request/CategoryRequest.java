package com.icebear2n2.productservice.domain.request;

import com.icebear2n2.productservice.domain.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    private String categoryName;

    public Category toEntity() {
        return Category.builder()
                .categoryName(categoryName)
                .build();
    }
}
