package com.icebear2n2.productservice.product.controller;

import com.icebear2n2.productservice.domain.request.CreateProductDetailRequest;
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
    public ResponseEntity<String> createProductDetail(@RequestBody CreateProductDetailRequest createProductDetailRequest) {
        productDetailService.createProductDetail(createProductDetailRequest);
        return new ResponseEntity<>("Product Detail Info created Successfully!", HttpStatus.CREATED);
    }

}
