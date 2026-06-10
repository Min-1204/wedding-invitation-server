package com.wedding.wedding_invitation.domain.order.repository;

import com.wedding.wedding_invitation.domain.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
