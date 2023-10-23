package com.icebear2n2.productservice.domain.response;

import com.icebear2n2.productservice.domain.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {

    private boolean success;
    private String message;
    private CategoryData data;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CategoryData {
        private Long categoryId;
        private String categoryName;
        private String createdAt;
        private String updatedAt;

        public CategoryData(Category category) {
            this.categoryId = category.getCategoryId();
            this.categoryName = category.getCategoryName();
            this.createdAt = category.getCreatedAt().toString();
            this.updatedAt = category.getUpdatedAt().toString();
        }
    }

    public static CategoryResponse success(Category category) {
        return new CategoryResponse(true, "Success", new CategoryData(category));
    }

    public static CategoryResponse failure(String message) {
        return new CategoryResponse(false, message, null);
    }
}