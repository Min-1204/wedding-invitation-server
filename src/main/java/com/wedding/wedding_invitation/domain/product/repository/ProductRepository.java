package com.wedding.wedding_invitation.domain.product.repository;

import com.wedding.wedding_invitation.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
