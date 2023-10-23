package com.icebear2n2.productservice.product.controller;

import com.icebear2n2.productservice.domain.request.CreateCategoryRequest;
import com.icebear2n2.productservice.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {
//    TODO: CRUD : CREATE
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<String> createCategory(@RequestBody CreateCategoryRequest createCategoryRequest) {
        categoryService.createCategory(createCategoryRequest);
        return new ResponseEntity<>("Category created successfully!", HttpStatus.CREATED);
    }



}
