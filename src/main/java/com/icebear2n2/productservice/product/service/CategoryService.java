package com.icebear2n2.productservice.product.service;

import com.icebear2n2.productservice.domain.repository.CategoryRepository;
import com.icebear2n2.productservice.domain.request.CreateCategoryRequest;
import com.icebear2n2.productservice.domain.response.CategoryResponse;
import com.icebear2n2.productservice.exception.ErrorCode;
import com.icebear2n2.productservice.exception.ProductServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryResponse createCategory(CreateCategoryRequest createCategoryRequest) {
        if (categoryRepository.existsByCategoryName(createCategoryRequest.getCategoryName())) {
            return CategoryResponse.failure(ErrorCode.DUPLICATED_CATEGORY_NAME.toString());
        }

        try {
            return CategoryResponse.success(categoryRepository.save(createCategoryRequest.toEntity()));
        } catch (Exception e) {
            return CategoryResponse.failure(ErrorCode.INTERNAL_SERVER_ERROR.toString());
        }
    }

    public List<CategoryResponse.CategoryData> findCategoriesCreatedAfter(Timestamp createdAt) {
        return categoryRepository.findByCreatedAtAfter(createdAt)
                .stream()
                .map(CategoryResponse.CategoryData::new)
                .collect(Collectors.toList());
    }

    public List<CategoryResponse.CategoryData> findCategoriesUpdatedAfter(Timestamp updatedAt) {
        return categoryRepository.findByUpdatedAtAfter(updatedAt)
                .stream()
                .map(CategoryResponse.CategoryData::new)
                .collect(Collectors.toList());
    }

    public List<CategoryResponse.CategoryData> findCategoriesByNameContaining(String keyword) {
        return categoryRepository.findByCategoryNameContaining(keyword)
                .stream()
                .map(CategoryResponse.CategoryData::new)
                .collect(Collectors.toList());
    }

    public List<CategoryResponse.CategoryData> findAllCategoriesOrderedByName() {
        return categoryRepository.findByOrderByCategoryNameAsc()
                .stream()
                .map(CategoryResponse.CategoryData::new)
                .collect(Collectors.toList());
    }

    public List<CategoryResponse.CategoryData> findAllCategoriesOrderedByRecentUpdate() {
        return categoryRepository.findByOrderByUpdatedAtDesc()
                .stream()
                .map(CategoryResponse.CategoryData::new)
                .collect(Collectors.toList());
    }

}