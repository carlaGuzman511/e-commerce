package com.kiosco.product_service.repository;

import com.kiosco.product_service.domain.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {

    List<ProductVariant> findByProductId(Long productId);

    List<ProductVariant> findByProductTenantIdAndProductCodeIgnoreCase(String tenantId, String productCode);

    boolean existsByProductIdAndProductCodeIgnoreCase(Long productId, String productCode);
    List<ProductVariant> findAllByProductId(Long productId);
}