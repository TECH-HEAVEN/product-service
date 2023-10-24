package com.icebear2n2.productservice.product.controller;

import com.icebear2n2.productservice.domain.request.ProductRequest;
import com.icebear2n2.productservice.domain.response.ProductResponse;
import com.icebear2n2.productservice.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.createProduct(productRequest);
        if (productResponse.isSuccess()) {
            return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(productResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getProducts(
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) Integer price,
            @RequestParam(required = false) Integer discountPrice,
            @RequestParam(required = false) Timestamp saleStartDate,
            @RequestParam(required = false) Timestamp saleEndDate,
            @RequestParam(required = false) Timestamp createdAt,
            @RequestParam(required = false) Timestamp saleStartTimestamp,
            @RequestParam(required = false) Timestamp saleEndTimestamp) {

        if (productName != null) {
            return new ResponseEntity<>(productService.findProductByName(productName), HttpStatus.OK);
        } else if (categoryName != null) {
            return new ResponseEntity<>(productService.findProductsByCategory(categoryName), HttpStatus.OK);
        } else if (price != null) {
            return new ResponseEntity<>(productService.findProductsByPrice(price), HttpStatus.OK);
        } else if (discountPrice != null) {
            return new ResponseEntity<>(productService.findProductsByDiscountPrice(discountPrice), HttpStatus.OK);
        } else if (saleStartDate != null) {
            return new ResponseEntity<>(productService.findProductsBySaleStartDate(saleStartDate), HttpStatus.OK);
        } else if (saleEndDate != null) {
            return new ResponseEntity<>(productService.findProductsBySaleEndDate(saleEndDate), HttpStatus.OK);
        } else if (createdAt != null) {
            return new ResponseEntity<>(productService.findProductsAddedAfter(createdAt), HttpStatus.OK);
        } else if (saleStartTimestamp != null && saleEndTimestamp != null) {
            return new ResponseEntity<>(productService.findProductsBySaleStartAndEndDate(saleStartTimestamp, saleEndTimestamp), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
        }
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("productId") Long productId, @RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.updateProduct(productId, productRequest);
        if (productResponse.isSuccess()) {
            return new ResponseEntity<>(productResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(productResponse, HttpStatus.BAD_REQUEST);
        }
    }
}