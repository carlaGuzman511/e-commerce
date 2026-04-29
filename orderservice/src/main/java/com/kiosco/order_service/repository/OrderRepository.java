package com.kiosco.order_service.repository;

import com.kiosco.order_service.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    Optional<Order> findByIdAndTenantId(Long id, String tenantId);
}