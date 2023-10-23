package com.icebear2n2.productservice.product.service;

import com.icebear2n2.productservice.domain.repository.CategoryRepository;
import com.icebear2n2.productservice.domain.request.CreateCategoryRequest;
import com.icebear2n2.productservice.domain.response.CategoryResponse;
import com.icebear2n2.productservice.exception.ErrorCode;
import com.icebear2n2.productservice.exception.ProductServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

//    TODO: CREATE CATEGORY
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


}
