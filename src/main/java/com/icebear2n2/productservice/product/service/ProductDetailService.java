package com.icebear2n2.productservice.product.service;

import com.icebear2n2.productservice.domain.repository.ProductDetailRepository;
import com.icebear2n2.productservice.domain.repository.ProductRepository;
import com.icebear2n2.productservice.domain.request.CreateProductDetailRequest;
import com.icebear2n2.productservice.exception.ErrorCode;
import com.icebear2n2.productservice.exception.ProductServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductDetailService {
    private final ProductDetailRepository productDetailRepository;
    private final ProductRepository productRepository;

//    TODO: CREATE PRODUCT DETAIL

    public void createProductDetail(CreateProductDetailRequest createProductDetailRequest) {
        if (!productRepository.findByProductName(createProductDetailRequest.getProduct())) {
            throw new ProductServiceException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        try {
            productDetailRepository.save(createProductDetailRequest.toEntity());
        } catch (Exception e) {
            throw new ProductServiceException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
