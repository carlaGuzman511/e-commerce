package com.kiosco.product_service.dto;

import com.kiosco.product_service.domain.Product;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ProductSpecs {

    private ProductSpecs() {
    }

    public static Specification<Product> tenantIs(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<Product> categoryIs(Long categoryId) {
        return (root, query, cb) -> cb.equal(root.get("category").get("id"), categoryId);
    }


    public static Specification<Product> isActive() {
        return (root, query, cb) -> cb.isTrue(root.get("status"));
    }

    public static Specification<Product> lowStock() {
        return (root, query, cb) -> {
            Join<Object, Object> variants = root.join("variants", JoinType.LEFT);
            Join<Object, Object> branches = variants.join("productBranches", JoinType.LEFT);

            return cb.lessThan(branches.get("stock"), branches.get("minStock"));
        };
    }


    public static Specification<Product> expiringSoon(LocalDate limitDate) {
        return (root, query, cb) -> {
            Join<Object, Object> variants = root.join("variants", JoinType.LEFT);
            Join<Object, Object> batches = variants.join("batches", JoinType.LEFT);
            return cb.lessThanOrEqualTo(batches.get("expiryDate"), limitDate);
        };
    }


    public static Specification<Product> createdRecently(LocalDate fromDate) {
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("createdAt").as(LocalDate.class), fromDate);
    }

    public static Specification<Product> nameContains(String keyword) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("name")), "%" + keyword.toLowerCase() + "%");
    }
}
