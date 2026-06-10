package com.wedding.wedding_invitation.domain.order.repository;

import com.wedding.wedding_invitation.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
