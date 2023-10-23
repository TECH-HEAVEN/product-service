package com.icebear2n2.productservice.product.controller;

import com.icebear2n2.productservice.domain.request.CreateProductRequest;
import com.icebear2n2.productservice.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {
    //    TODO: CRUD : CREATE
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody CreateProductRequest createProductRequest) {
        productService.createProduct(createProductRequest);
        return new ResponseEntity<>("Product created successfully!", HttpStatus.CREATED);
    }
}
