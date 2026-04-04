package com.wedding.wedding_invitation.domain.product.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // 상품 고유번호

    @Enumerated(EnumType.STRING)
    private ProductCategory category;            // 카테고리

    @Column(nullable = false)
    private String name;                // 상품명

    @Column(nullable = false)
    private int price;                   // 상품가격

    private String description;          // 상품설명

    private String productImageUrl;      // 상품이미지

    private String descriptionImageUrl;  // 상품상세이미지



}
