package com.icebear2n2.productservice.product.controller;

import com.icebear2n2.productservice.domain.request.CreateProductDetailRequest;
import com.icebear2n2.productservice.domain.response.ProductDetailResponse;
import com.icebear2n2.productservice.product.service.ProductDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product-detail")
public class ProductDetailController {
    //    TODO: CRUD : CREATE
    private final ProductDetailService productDetailService;

    @PostMapping
    public ResponseEntity<ProductDetailResponse> createProductDetail(@RequestBody CreateProductDetailRequest createProductDetailRequest) {
        ProductDetailResponse productDetailResponse = productDetailService.createProductDetail(createProductDetailRequest);
        if (productDetailResponse.isSuccess()) {
            return new ResponseEntity<>(productDetailResponse, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(productDetailResponse, HttpStatus.BAD_REQUEST);
        }

    }

}
