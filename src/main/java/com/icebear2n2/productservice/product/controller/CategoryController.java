package com.icebear2n2.productservice.product.controller;

import com.icebear2n2.productservice.domain.request.CreateCategoryRequest;
import com.icebear2n2.productservice.domain.response.CategoryResponse;
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
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CreateCategoryRequest createCategoryRequest) {
        CategoryResponse categoryResponse = categoryService.createCategory(createCategoryRequest);
        if (categoryResponse.isSuccess()) {
            return new ResponseEntity<>(categoryResponse, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(categoryResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
