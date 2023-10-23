package com.icebear2n2.productservice.product.service;

import com.icebear2n2.productservice.domain.repository.CategoryRepository;
import com.icebear2n2.productservice.domain.request.CreateCategoryRequest;
import com.icebear2n2.productservice.exception.ErrorCode;
import com.icebear2n2.productservice.exception.ProductServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

//    TODO: CREATE CATEGORY
    public void createCategory(CreateCategoryRequest createCategoryRequest) {
        if (categoryRepository.existsByCategoryName(createCategoryRequest.getCategoryName())) {
            throw new ProductServiceException(ErrorCode.DUPLICATED_CATEGORY_NAME);
        }

        try {
            categoryRepository.save(createCategoryRequest.toEntity());
        } catch (Exception e) {
            throw new ProductServiceException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

}
