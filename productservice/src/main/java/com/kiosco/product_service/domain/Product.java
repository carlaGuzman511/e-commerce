package com.kiosco.product_service.domain;

import com.kiosco.product_service.dto.TenantEntity;
import com.kiosco.product_service.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "product", schema = "dbo")
public class Product extends TenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "text")
    private String technicalDetails;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private Integer minStock;

    @Column(nullable = false)
    private Boolean active = true;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private ProductStatus status = ProductStatus.DRAFT;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVariant> variants;
}
