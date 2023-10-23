package com.icebear2n2.productservice.domain.repository;

import com.icebear2n2.productservice.domain.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
}
