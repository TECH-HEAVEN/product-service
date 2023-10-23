package com.icebear2n2.productservice.product.service;

import com.icebear2n2.productservice.domain.entity.Product;
import com.icebear2n2.productservice.domain.entity.ProductDetail;
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

    public void createProductDetail(CreateProductDetailRequest createProductDetailRequest) {
        Product product = productRepository.findByProductName(createProductDetailRequest.getProductName());

        if (product == null) {
            throw new ProductServiceException(ErrorCode.PRODUCT_NOT_FOUND);
        }


        if (productDetailRepository.existsByProduct(product)) {
            throw new ProductServiceException(ErrorCode.DUPLICATED_PRODUCT_DETAIL);
        }

        try {
            ProductDetail productDetail = createProductDetailRequest.toEntity();
            productDetail.setProduct(product);
            productDetailRepository.save(productDetail);
        } catch (Exception e) {
            throw new ProductServiceException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}