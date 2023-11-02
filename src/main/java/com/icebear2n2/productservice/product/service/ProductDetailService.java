package com.icebear2n2.productservice.product.service;

import com.icebear2n2.productservice.domain.entity.Product;
import com.icebear2n2.productservice.domain.entity.ProductDetail;
import com.icebear2n2.productservice.domain.repository.ProductDetailRepository;
import com.icebear2n2.productservice.domain.repository.ProductRepository;
import com.icebear2n2.productservice.domain.request.ProductDetailIDRequest;
import com.icebear2n2.productservice.domain.request.ProductDetailRequest;
import com.icebear2n2.productservice.domain.request.ProductIDRequest;
import com.icebear2n2.productservice.domain.response.ProductDetailResponse;
import com.icebear2n2.productservice.exception.ErrorCode;
import com.icebear2n2.productservice.exception.ProductServiceException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductDetailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductDetailService.class);
    private final ProductDetailRepository productDetailRepository;
    private final ProductRepository productRepository;

    public ProductDetailResponse createProductDetail(ProductDetailRequest productDetailRequest) {
        Product product = productRepository.findByProductName(productDetailRequest.getProductName());

        if (product == null) {
            return ProductDetailResponse.failure(ErrorCode.PRODUCT_NOT_FOUND.toString());
        }



        try {
            ProductDetail productDetail = productDetailRequest.toEntity();
            productDetail.setProduct(product);
            return ProductDetailResponse.success(productDetailRepository.save(productDetail));
        } catch (Exception e) {
            LOGGER.info("INTERNAL_SERVER_ERROR: {}", e.toString());
            return ProductDetailResponse.failure(ErrorCode.INTERNAL_SERVER_ERROR.toString());
        }
    }

    public Page<ProductDetailResponse.ProductDetailData> findAllByProduct(ProductIDRequest productIDRequest, PageRequest pageRequest) {
        Product product = productRepository.findById(productIDRequest.getProductId()).orElseThrow(() -> new ProductServiceException(ErrorCode.PRODUCT_NOT_FOUND));
        Page<ProductDetail> all = productDetailRepository.findAllByProduct(product, pageRequest);
        return all.map(ProductDetailResponse.ProductDetailData::new);
    }

    public ProductDetailResponse updateProductDetail(ProductDetailRequest productDetailRequest) {

        if (!productDetailRepository.existsById(productDetailRequest.getProductDetailId())) {
            return ProductDetailResponse.failure(ErrorCode.PRODUCT_DETAIL_NOT_FOUND.toString());
        }

        try {
            ProductDetail existingProductDetail = productDetailRepository.findById(productDetailRequest.getProductDetailId()).orElseThrow(() -> new ProductServiceException(ErrorCode.PRODUCT_DETAIL_NOT_FOUND));
            if (!existingProductDetail.getProduct().getProductName().equals(productDetailRequest.getProductName())) {
                return ProductDetailResponse.failure(ErrorCode.PRODUCT_NOT_FOUND.toString());
            }

            productDetailRequest.updateProductDetailIfNotNull(existingProductDetail, productRepository);
            productDetailRepository.save(existingProductDetail);
            return ProductDetailResponse.success(existingProductDetail);
        } catch (Exception e) {
            LOGGER.info("INTERNAL_SERVER_ERROR: {}", e.toString());
            return ProductDetailResponse.failure(ErrorCode.INTERNAL_SERVER_ERROR.toString());
        }
    }

    public void removeProductDetail(ProductDetailIDRequest productDetailIDRequest) {
        if (!productDetailRepository.existsById(productDetailIDRequest.getProductDetailId())) {
            throw new ProductServiceException(ErrorCode.PRODUCT_DETAIL_NOT_FOUND);
        }
        try {
            productDetailRepository.deleteById(productDetailIDRequest.getProductDetailId());
        } catch (Exception e) {
            LOGGER.info("INTERNAL_SERVER_ERROR: {}", e.toString());
            throw new ProductServiceException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

}
