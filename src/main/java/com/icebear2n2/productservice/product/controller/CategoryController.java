package com.icebear2n2.productservice.product.controller;

import com.icebear2n2.productservice.domain.request.CategoryRequest;
import com.icebear2n2.productservice.domain.response.CategoryResponse;
import com.icebear2n2.productservice.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = categoryService.createCategory(categoryRequest);
        if (categoryResponse.isSuccess()) {
            return new ResponseEntity<>(categoryResponse, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(categoryResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse.CategoryData>> getCategories(
            @RequestParam(required = false) Timestamp createdAt,
            @RequestParam(required = false) Timestamp updatedAt,
            @RequestParam(required = false) String keyword) {

        if (createdAt != null) {
            return new ResponseEntity<>(categoryService.findCategoriesCreatedAfter(createdAt), HttpStatus.OK);
        } else if (updatedAt != null) {
            return new ResponseEntity<>(categoryService.findCategoriesUpdatedAfter(updatedAt), HttpStatus.OK);
        } else if (keyword != null) {
            return new ResponseEntity<>(categoryService.findCategoriesByNameContaining(keyword), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(categoryService.findAllCategories(), HttpStatus.OK);
        }
    }

    @GetMapping("/ordered-by-name")
    public ResponseEntity<List<CategoryResponse.CategoryData>> getAllCategoriesOrderedByName() {
        return new ResponseEntity<>(categoryService.findAllCategoriesOrderedByName(), HttpStatus.OK);
    }

    @GetMapping("/recent-updates")
    public ResponseEntity<List<CategoryResponse.CategoryData>> getAllCategoriesOrderedByRecentUpdate() {
        return new ResponseEntity<>(categoryService.findAllCategoriesOrderedByRecentUpdate(), HttpStatus.OK);
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable("categoryId") Long categoryId, @RequestBody CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = categoryService.updateCategory(categoryId, categoryRequest);
        if (categoryResponse.isSuccess()) {
            return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(categoryResponse, HttpStatus.BAD_REQUEST);
        }
    }
}