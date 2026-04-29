package com.kiosco.product_service.repository;

import com.kiosco.product_service.domain.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

    Optional<Branch> findByIdAndTenantId(Long id, String tenantId);

    boolean existsByTenantIdAndNameIgnoreCase(String tenantId, String name);
}