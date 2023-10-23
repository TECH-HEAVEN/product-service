package com.icebear2n2.productservice.product.service;

import com.icebear2n2.productservice.domain.entity.Product;
import com.icebear2n2.productservice.domain.entity.ProductDetail;
import com.icebear2n2.productservice.domain.repository.ProductDetailRepository;
import com.icebear2n2.productservice.domain.repository.ProductRepository;
import com.icebear2n2.productservice.domain.request.CreateProductDetailRequest;
import com.icebear2n2.productservice.domain.response.ProductDetailResponse;
import com.icebear2n2.productservice.exception.ErrorCode;
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

    public ProductDetailResponse createProductDetail(CreateProductDetailRequest createProductDetailRequest) {
        Product product = productRepository.findByProductName(createProductDetailRequest.getProductName());

        if (product == null) {
            return ProductDetailResponse.failure(ErrorCode.PRODUCT_NOT_FOUND.toString());
        }


        if (productDetailRepository.existsByProduct(product)) {
            return ProductDetailResponse.failure(ErrorCode.DUPLICATED_PRODUCT_DETAIL.toString());
        }

        try {
            ProductDetail productDetail = createProductDetailRequest.toEntity();
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

    public List<ProductDetailResponse.ProductDetailData> findProductDetailsByStockQuantity(Integer quantity) {
        return productDetailRepository.findByStockQuantityGreaterThan(quantity)
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

    public ProductDetailResponse.ProductDetailData findDetailByProduct(Product product) {
        ProductDetail productDetail = productDetailRepository.findByProduct(product);
        if (productDetail != null) {
            return new ProductDetailResponse.ProductDetailData(productDetail);
        }
        return ProductDetailResponse.failure(ErrorCode.PRODUCT_DETAIL_NOT_FOUND.toString()).getProductDetailData();
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
}
