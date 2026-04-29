package com.kiosco.tenant_service.repository;

import com.kiosco.tenant_service.domain.Branch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long>, JpaSpecificationExecutor<Branch> {
    Page<Branch> findByIdAndTenantIdAndActiveTrue(Long storeId, String tenantId, Pageable pageable);
    Optional<Branch>findByIdAndStoreIdAndTenantIdAndActiveTrue(Long branchId, Long storeId, String tenantId);
}