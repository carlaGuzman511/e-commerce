package com.kiosco.product_service.repository;

import com.kiosco.product_service.domain.ProductBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductBranchRepository extends JpaRepository<ProductBranch, Long> {

    List<ProductBranch> findAllByBranch_Id(Long branchId);

    Optional<ProductBranch> findByBranch_IdAndProductVariant_Id(Long branchId, Long variantId);

    Optional<ProductBranch> findByBranch_IdAndProductVariant_Product_Id(Long branchId, Long productId);
}
