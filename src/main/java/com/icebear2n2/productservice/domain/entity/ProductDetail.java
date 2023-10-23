package com.icebear2n2.productservice.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_detail")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetail {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productDetailId;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private String productColor;
    private String productSize;
    private Integer stockQuantity;

}
