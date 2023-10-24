package com.icebear2n2.productservice.product.controller;

import com.icebear2n2.productservice.domain.request.ProductDetailRequest;
import com.icebear2n2.productservice.domain.response.ProductDetailResponse;
import com.icebear2n2.productservice.product.service.ProductDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product-detail")
public class ProductDetailController {
    private final ProductDetailService productDetailService;

    @PostMapping
    public ResponseEntity<ProductDetailResponse> createProductDetail(@RequestBody ProductDetailRequest productDetailRequest) {
        ProductDetailResponse productDetailResponse = productDetailService.createProductDetail(productDetailRequest);
        if (productDetailResponse.isSuccess()) {
            return new ResponseEntity<>(productDetailResponse, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(productDetailResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getProductDetails(
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) Timestamp updatedAt) {

        if (color != null && size == null) {
            return new ResponseEntity<>(productDetailService.findProductDetailsByColor(color), HttpStatus.OK);
        } else if (size != null && color == null) {
            return new ResponseEntity<>(productDetailService.findProductDetailsBySize(size), HttpStatus.OK);
        } else if (updatedAt != null) {
            return new ResponseEntity<>(productDetailService.findProductDetailsUpdatedAfter(updatedAt), HttpStatus.OK);
        } else if (color != null) {
            return new ResponseEntity<>(productDetailService.findProductDetailsByColorAndSize(color, size), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(productDetailService.getAllProductDetails(), HttpStatus.OK);
        }
    }

    @PutMapping("/update/{productDetailId}")
    public ResponseEntity<ProductDetailResponse> updateProductDetail(@PathVariable("productDetailId") Long productDetailId, @RequestBody ProductDetailRequest productDetailRequest) {
        ProductDetailResponse productDetailResponse = productDetailService.updateProductDetail(productDetailId, productDetailRequest);

        if (productDetailResponse.isSuccess()) {
            return new ResponseEntity<>(productDetailResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(productDetailResponse, HttpStatus.BAD_REQUEST);
        }
    }

}