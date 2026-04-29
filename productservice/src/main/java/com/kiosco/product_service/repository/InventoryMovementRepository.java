package com.kiosco.product_service.repository;

import com.kiosco.product_service.domain.InventoryMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryMovementRepository extends JpaRepository<InventoryMovement, Long> {

    List<InventoryMovement> findAllByVariantId(Long variantId);
    List<InventoryMovement> findAllByBranchId(Long branchId);
    List<InventoryMovement> findAllByBranchIdAndVariantId(Long branchId, Long variantId);
}
