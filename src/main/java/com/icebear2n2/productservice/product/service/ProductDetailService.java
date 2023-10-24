package com.icebear2n2.productservice.product.service;

import com.icebear2n2.productservice.domain.entity.Product;
import com.icebear2n2.productservice.domain.entity.ProductDetail;
import com.icebear2n2.productservice.domain.repository.ProductDetailRepository;
import com.icebear2n2.productservice.domain.repository.ProductRepository;
import com.icebear2n2.productservice.domain.request.ProductDetailRequest;
import com.icebear2n2.productservice.domain.response.ProductDetailResponse;
import com.icebear2n2.productservice.exception.ErrorCode;
import com.icebear2n2.productservice.exception.ProductServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductDetailService {
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
            return ProductDetailResponse.failure(ErrorCode.INTERNAL_SERVER_ERROR.toString());
        }
    }

    public List<ProductDetailResponse.ProductDetailData> findProductDetailsByColor(String color) {
        return productDetailRepository.findByProductColorsContains(color)
                .stream()
                .map(ProductDetailResponse.ProductDetailData::new)
                .collect(Collectors.toList());
    }

    public List<ProductDetailResponse.ProductDetailData> findProductDetailsBySize(String size) {
        return productDetailRepository.findByProductSizesContains(size)
                .stream()
                .map(ProductDetailResponse.ProductDetailData::new)
                .collect(Collectors.toList());
    }


    public List<ProductDetailResponse.ProductDetailData> findProductDetailsUpdatedAfter(Timestamp updatedAt) {
        return productDetailRepository.findByUpdatedAtAfter(updatedAt)
                .stream()
                .map(ProductDetailResponse.ProductDetailData::new)
                .collect(Collectors.toList());
    }

    public List<ProductDetailResponse.ProductDetailData> findProductDetailsByColorAndSize(String color, String size) {
        return productDetailRepository.findByProductColorsContainsAndProductSizesContains(color, size)
                .stream()
                .map(ProductDetailResponse.ProductDetailData::new)
                .collect(Collectors.toList());
    }

    public List<ProductDetailResponse.ProductDetailData> getAllProductDetails() {
       return productDetailRepository.findAll()
                .stream()
                .map(ProductDetailResponse.ProductDetailData::new)
                .collect(Collectors.toList());
    }

    public ProductDetailResponse updateProductDetail(Long productDetailId, ProductDetailRequest productDetailRequest) {

        if (!productDetailRepository.existsById(productDetailId)) {
            return ProductDetailResponse.failure(ErrorCode.PRODUCT_DETAIL_NOT_FOUND.toString());
        }

        try {
            ProductDetail existingProductDetail = productDetailRepository.findById(productDetailId).orElseThrow(() -> new ProductServiceException(ErrorCode.PRODUCT_DETAIL_NOT_FOUND));
            if (!existingProductDetail.getProduct().getProductName().equals(productDetailRequest.getProductName())) {
                return ProductDetailResponse.failure(ErrorCode.PRODUCT_NOT_FOUND.toString());
            }

            productDetailRequest.updateProductDetailIfNotNull(existingProductDetail, productRepository);
            productDetailRepository.save(existingProductDetail);
            return ProductDetailResponse.success(existingProductDetail);
        } catch (Exception e) {
            return ProductDetailResponse.failure(ErrorCode.INTERNAL_SERVER_ERROR.toString());
        }
    }


}
