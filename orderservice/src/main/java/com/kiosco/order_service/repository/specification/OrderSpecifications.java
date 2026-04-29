package com.kiosco.order_service.repository.specification;

import com.kiosco.order_service.domain.Order;
import com.kiosco.order_service.enums.OrderStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;

public class OrderSpecifications {

    public static Specification<Order> hasTenantId(String tenantId) {
        return (root, query, cb) ->
                tenantId == null ? cb.conjunction() : cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<Order> hasUserId(Long userId) {
        return (root, query, cb) ->
                userId == null ? cb.conjunction() : cb.equal(root.get("userId"), userId);
    }

    public static Specification<Order> hasStatus(OrderStatus status) {
        return (root, query, cb) ->
                status == null ? cb.conjunction() : cb.equal(root.get("orderStatus"), status);
    }

    public static Specification<Order> createdBetween(Instant startDate, Instant endDate) {
        return (root, query, cb) -> {
            if (startDate != null && endDate != null)
                return cb.between(root.get("createdAt"), startDate, endDate);
            if (startDate != null)
                return cb.greaterThanOrEqualTo(root.get("createdAt"), startDate);
            if (endDate != null)
                return cb.lessThanOrEqualTo(root.get("createdAt"), endDate);
            return cb.conjunction();
        };
    }
}

