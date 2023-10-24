package com.icebear2n2.productservice.product.service;

import com.icebear2n2.productservice.domain.entity.Product;
import com.icebear2n2.productservice.domain.repository.CategoryRepository;
import com.icebear2n2.productservice.domain.repository.ProductRepository;
import com.icebear2n2.productservice.domain.request.ProductRequest;
import com.icebear2n2.productservice.domain.response.ProductResponse;
import com.icebear2n2.productservice.exception.ErrorCode;
import com.icebear2n2.productservice.exception.ProductServiceException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);


    public ProductResponse createProduct(ProductRequest productRequest) {
        if (!categoryRepository.existsByCategoryName(productRequest.getCategoryName())) {
            return ProductResponse.failure(ErrorCode.CATEGORY_NOT_FOUND.toString());
        }

        if (productRepository.existsByProductName(productRequest.getProductName())) {
            return ProductResponse.failure(ErrorCode.DUPLICATED_PRODUCT_NAME.toString());
        }

        try {
            Product product = productRequest.toEntity();
            product.setCategory(categoryRepository.findByCategoryName(productRequest.getCategoryName()));
            Product savedProduct = productRepository.save(product);
            return ProductResponse.success(savedProduct);
        } catch (Exception e) {
            LOGGER.error("Error occurred while creating product", e);
            return ProductResponse.failure(ErrorCode.INTERNAL_SERVER_ERROR.toString());
        }
    }


    public ProductResponse findProductByName(String productName) {
        Product product = productRepository.findByProductName(productName);
        if (product != null) {
            return ProductResponse.success(product);
        }
        return ProductResponse.failure(ErrorCode.PRODUCT_NOT_FOUND.toString());
    }

    public List<ProductResponse.ProductData> findProductsByPrice(Integer price) {
        return productRepository.findByProductPriceGreaterThanEqual(price)
                .stream()
                .map(ProductResponse.ProductData::new)
                .collect(Collectors.toList());
    }

    public List<ProductResponse.ProductData> findProductsByDiscountPrice(Integer discountPrice) {
        return productRepository.findByDiscountPriceLessThanEqual(discountPrice)
                .stream()
                .map(ProductResponse.ProductData::new)
                .collect(Collectors.toList());
    }

    public List<ProductResponse.ProductData> findProductsBySaleStartDate(Timestamp date) {
        return productRepository.findBySaleStartDateBefore(date)
                .stream()
                .map(ProductResponse.ProductData::new)
                .collect(Collectors.toList());
    }

    public List<ProductResponse.ProductData> findProductsBySaleEndDate(Timestamp date) {
        return productRepository.findBySaleEndDateAfter(date)
                .stream()
                .map(ProductResponse.ProductData::new)
                .collect(Collectors.toList());
    }

    public List<ProductResponse.ProductData> findProductsByCategory(String categoryName) {
        return productRepository.findByCategoryContaining(categoryName)
                .stream()
                .map(ProductResponse.ProductData::new)
                .collect(Collectors.toList());
    }

    public List<ProductResponse.ProductData> findProductsAddedAfter(Timestamp createdAt) {
        return productRepository.findByCreatedAtAfter(createdAt)
                .stream()
                .map(ProductResponse.ProductData::new)
                .collect(Collectors.toList());
    }

    public List<ProductResponse.ProductData> findProductsBySaleStartAndEndDate(Timestamp startTimestamp, Timestamp endTimestamp) {
        return productRepository.findBySaleStartDateBeforeAndSaleEndDateAfter(startTimestamp, endTimestamp)
                .stream()
                .map(ProductResponse.ProductData::new)
                .collect(Collectors.toList());
    }

    public List<ProductResponse.ProductData> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponse.ProductData::new)
                .collect(Collectors.toList());
    }

    //   TODO: UPDATE
    public ProductResponse updateProduct(Long productId, ProductRequest productRequest) {

        if (!productRepository.existsByProductId(productId)) {
            return ProductResponse.failure(ErrorCode.PRODUCT_NOT_FOUND.toString());
        }

        if (productRepository.existsByProductName(productRequest.getProductName())) {
            return ProductResponse.failure(ErrorCode.DUPLICATED_PRODUCT_NAME.toString());
        }

        try {
            Product existingProduct = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductServiceException(ErrorCode.PRODUCT_NOT_FOUND));
            productRequest.updateProductIfNotNull(existingProduct, categoryRepository);
            productRepository.save(existingProduct);
            return ProductResponse.success(existingProduct);
        } catch (Exception e) {
            LOGGER.info("ERROR OCCURS {}", e.toString());
            return ProductResponse.failure(ErrorCode.INTERNAL_SERVER_ERROR.toString());
        }
    }
}