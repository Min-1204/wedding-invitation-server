package com.wedding.wedding_invitation.domain.cart.repository;

import com.wedding.wedding_invitation.domain.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
