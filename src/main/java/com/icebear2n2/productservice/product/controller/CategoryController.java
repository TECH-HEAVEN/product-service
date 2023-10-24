package com.icebear2n2.productservice.product.controller;

import com.icebear2n2.productservice.domain.request.CategoryRequest;
import com.icebear2n2.productservice.domain.response.CategoryResponse;
import com.icebear2n2.productservice.product.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 새로운 카테고리를 생성합니다.
     *
     * @param categoryRequest 카테고리 상세 정보를 포함한 요청 본문.
     * @return 상태와 데이터를 포함한 응답.
     */
    @Operation(summary = "새로운 카테고리 생성")
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = categoryService.createCategory(categoryRequest);
        if (categoryResponse.isSuccess()) {
            return new ResponseEntity<>(categoryResponse, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(categoryResponse, HttpStatus.BAD_REQUEST);
        }
    }



    /**
     * 생성일, 수정일 또는 키워드를 기반으로 카테고리를 검색합니다.
     *
     * @return 카테고리 목록.
     */
    @Operation(summary = "카테고리 검색")
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

    /**
     * 이름 순으로 모든 카테고리를 검색합니다.
     *
     * @return 이름 순으로 정렬된 카테고리 목록.
     */
    @Operation(summary = "이름 순 카테고리 검색")
    @GetMapping("/order-by-name")
    public ResponseEntity<List<CategoryResponse.CategoryData>> getAllCategoriesOrderedByName() {
        return new ResponseEntity<>(categoryService.findAllCategoriesOrderedByName(), HttpStatus.OK);
    }


    /**
     * 최근 업데이트된 순으로 카테고리를 검색합니다.
     *
     * @return 최근 업데이트된 순으로 정렬된 카테고리 목록.
     */
    @Operation(summary = "최근 업데이트된 순 카테고리 검색")
    @GetMapping("/recent")
    public ResponseEntity<List<CategoryResponse.CategoryData>> getAllCategoriesOrderedByRecentUpdate() {
        return new ResponseEntity<>(categoryService.findAllCategoriesOrderedByRecentUpdate(), HttpStatus.OK);
    }

    /**
     * 기존 카테고리를 업데이트합니다.
     *
     * @param categoryId 업데이트할 카테고리의 ID.
     * @param categoryRequest 카테고리 상세 정보를 포함한 요청 본문.
     * @return 상태와 데이터를 포함한 응답.
     */
    @Operation(summary = "카테고리 업데이트")
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable("categoryId") Long categoryId, @RequestBody CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = categoryService.updateCategory(categoryId, categoryRequest);
        if (categoryResponse.isSuccess()) {
            return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(categoryResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 카테고리를 삭제합니다.
     *
     * @param categoryId 삭제할 카테고리의 ID.
     * @return 삭제 성공 메시지와 함께하는 응답.
     */
    @Operation(summary = "카테고리 삭제")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> removeCategory(@PathVariable("categoryId") Long categoryId) {
        categoryService.removeCategory(categoryId);
        return new ResponseEntity<>("Category removed successfully.", HttpStatus.OK);
    }
}