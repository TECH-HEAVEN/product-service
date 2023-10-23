package com.icebear2n2.productservice.domain.repository;

import com.icebear2n2.productservice.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByCategoryName(String categoryName);
    boolean findByCategoryName(Category category);
}
