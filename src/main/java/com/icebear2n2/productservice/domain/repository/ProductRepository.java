package com.icebear2n2.productservice.domain.repository;

import com.icebear2n2.productservice.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByProductName(String ProductName);
    boolean findByProductName(Product product);
}
