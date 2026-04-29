package com.kiosco.tenant_service.repository.specification;

import com.kiosco.tenant_service.domain.Branch;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;

public class BranchSpecification {

    public static Specification<Branch> hasTenantId(String tenantId) {
        return (root, query, cb) ->
                tenantId == null ? cb.conjunction() : cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<Branch> hasStoreId(Long storeId) {
        return (root, query, cb) ->
                storeId == null ? cb.conjunction() : cb.equal(root.get("store").get("id"), storeId);
    }

    public static Specification<Branch> hasStatus(Boolean status) {
        return (root, query, cb) ->
                status == null ? cb.conjunction() : cb.equal(root.get("active"), status);
    }

    public static Specification<Branch> createdBetween(Instant startDate, Instant endDate) {
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