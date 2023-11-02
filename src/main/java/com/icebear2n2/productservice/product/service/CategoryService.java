package com.icebear2n2.productservice.product.service;

import com.icebear2n2.productservice.domain.entity.Category;
import com.icebear2n2.productservice.domain.repository.CategoryRepository;
import com.icebear2n2.productservice.domain.request.CategoryRequest;
import com.icebear2n2.productservice.domain.response.CategoryResponse;
import com.icebear2n2.productservice.exception.ErrorCode;
import com.icebear2n2.productservice.exception.ProductServiceException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryService.class);
    private final CategoryRepository categoryRepository;

    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        if (categoryRepository.existsByCategoryName(categoryRequest.getCategoryName())) {
            return CategoryResponse.failure(ErrorCode.DUPLICATED_CATEGORY_NAME.toString());
        }

        try {
            return CategoryResponse.success(categoryRepository.save(categoryRequest.toEntity()));
        } catch (Exception e) {
            LOGGER.info("INTERNAL_SERVER_ERROR: {}", e.toString());
            return CategoryResponse.failure(ErrorCode.INTERNAL_SERVER_ERROR.toString());
        }
    }

    public Page<CategoryResponse.CategoryData> findByCategoryNameContaining(String categoryName, PageRequest pageRequest) {
        Page<Category> all = categoryRepository.findAllByCategoryNameContaining(categoryName, pageRequest);
        return all.map(CategoryResponse.CategoryData::new);

    }



//    TODO: UPDATE

    public CategoryResponse updateCategory(CategoryRequest categoryRequest) {
        if (!categoryRepository.existsById(categoryRequest.getCategoryId())) {
            return CategoryResponse.failure(ErrorCode.CATEGORY_NOT_FOUND.toString());
        }


        List<Category> categoriesWithSameName = categoryRepository.findByCategoryNameContaining(categoryRequest.getCategoryName());

        boolean isNameTakenByAnotherCategory = categoriesWithSameName.stream()
                .anyMatch(category -> !category.getCategoryId().equals(categoryRequest.getCategoryId()));

        if (isNameTakenByAnotherCategory) {
            return CategoryResponse.failure(ErrorCode.DUPLICATED_CATEGORY_NAME.toString());
        }

        try {
            Category existingCategory = categoryRepository.findById(categoryRequest.getCategoryId())
                    .orElseThrow(() -> new ProductServiceException(ErrorCode.CATEGORY_NOT_FOUND));
            categoryRequest.updateCategoryIfNotNull(existingCategory);
            categoryRepository.save(existingCategory);
            return CategoryResponse.success(existingCategory);
        } catch (Exception e) {
            LOGGER.info("INTERNAL_SERVER_ERROR: {}", e.toString());
            return CategoryResponse.failure(ErrorCode.INTERNAL_SERVER_ERROR.toString());
        }
    }

    public void removeCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ProductServiceException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        try {
            categoryRepository.deleteById(categoryId);
        } catch (DataIntegrityViolationException e) {
            LOGGER.info("DATA_INTEGRITY_VIOLATION_EXCEPTION: {}", e.toString());
            throw new ProductServiceException(ErrorCode.CATEGORY_HAS_RELATED_PRODUCTS);
        } catch (Exception e) {
            LOGGER.info("INTERNAL_SERVER_ERROR: {}", e.toString());
            throw new ProductServiceException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
