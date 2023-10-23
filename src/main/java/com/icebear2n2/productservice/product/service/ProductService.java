package com.icebear2n2.productservice.product.service;

import com.icebear2n2.productservice.domain.repository.CategoryRepository;
import com.icebear2n2.productservice.domain.repository.ProductRepository;
import com.icebear2n2.productservice.domain.request.CreateProductRequest;
import com.icebear2n2.productservice.exception.ErrorCode;
import com.icebear2n2.productservice.exception.ProductServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

//    TODO: CREATE PRODUCT

    public void createProduct(CreateProductRequest createProductRequest) {
        if (!categoryRepository.findByCategoryName(createProductRequest.getCategory())) {
            throw new ProductServiceException(ErrorCode.CATEGORY_NOT_FOUND);
        }

        if (productRepository.existsByProductName(createProductRequest.getProductName())) {
            throw new ProductServiceException(ErrorCode.DUPLICATED_PRODUCT_NAME);
        }

        try {
            productRepository.save(createProductRequest.toEntity());
        } catch (Exception e) {
            throw new ProductServiceException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
