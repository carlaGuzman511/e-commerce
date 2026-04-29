package com.kiosco.tenant_service.repository;

import com.kiosco.tenant_service.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long>, JpaSpecificationExecutor<Store> {
    Optional<Store> findByTenantIdAndActiveTrue(String tenantId);
    Optional<Store> findByIdAndActiveTrue(Long storeId);
}