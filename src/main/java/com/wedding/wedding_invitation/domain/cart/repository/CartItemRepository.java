package com.wedding.wedding_invitation.domain.cart.repository;

import com.wedding.wedding_invitation.domain.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
