package com.kiosco.product_service.repository;

import com.kiosco.product_service.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByTenantIdAndNameIgnoreCaseAndActiveTrue(String tenantId, String name);

    Optional<Category> findByIdAndTenantId(Long id, String tenantId);

    Optional<Category> findByTenantIdAndNameIgnoreCase(String tenantId, String name);

    Optional<Category> findByTenantIdAndNameIgnoreCaseAndActiveFalse(String tenantId, String name);

    List<Category> findAllByTenantIdAndActiveTrueOrderByNameAsc(String tenantId);

}