package com.kiosco.order_service.repository;

import com.kiosco.order_service.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Optional<OrderItem> findByIdAndTenantId(Long id, String tenantId);
}