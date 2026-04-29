
package com.kiosco.product_service.repository;

import com.kiosco.product_service.domain.Product;
import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    Optional<Product> findByIdAndTenantId(Long id, String tenantId);

    Optional<Product> findByIdAndTenantIdAndActiveTrue(Long id, String tenantId);


    boolean existsByTenantIdAndNameIgnoreCase(String tenantId, String name);

    @Query("""
                SELECT DISTINCT p FROM Product p
                JOIN p.variants v
                JOIN v.productBranches pb
                WHERE p.tenantId = :tenantId
                  AND p.active = true
                  AND pb.stock > 0
            """)
    Page<Product> findAllWithStockByTenant(@Param("tenantId") String tenantId, Pageable pageable);


    @Query("""
                SELECT DISTINCT p FROM Product p
                JOIN FETCH p.variants v
                WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
                  AND (:categoryId IS NULL OR p.category.id = :categoryId)
                  AND (v.price BETWEEN :minPrice AND :maxPrice)
            """)
    Page<Product> search(Pageable pageable,
                         @Param("name") String name,
                         @Param("categoryId") Long categoryId,
                         @Param("minPrice") BigDecimal minPrice,
                         @Param("maxPrice") BigDecimal maxPrice);

    @Query("""
            SELECT DISTINCT p FROM Product p
            JOIN p.variants v
            WHERE p.tenantId = :tenantId
              AND p.active = true
              AND (COALESCE(:name, '') = '' OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
              AND (:categoryId IS NULL OR p.category.id = :categoryId)
              AND (:minPrice IS NULL OR v.price >= :minPrice)
              AND (:maxPrice IS NULL OR v.price <= :maxPrice)
            """)
    Page<Product> searchProducts(
            @Param("tenantId") String tenantId,
            @Param("name") String name,
            @Param("categoryId") Long categoryId,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            Pageable pageable
    );
}
