package com.kiosco.product_service.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "product_variant", schema = "dbo")
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false, unique = true)
    private String productCode;

    @Column(columnDefinition = "text")
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(columnDefinition = "text")
    private String attributes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id")
    private Batch batch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @Column(name = "expiration_date")
    private Instant expirationDate;

    private Instant createdAt;
    private Instant updatedAt;

    @OneToMany(mappedBy = "productVariant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductBranch> productBranches;

    @ElementCollection
    @CollectionTable(
            name = "product_variant_images",
            joinColumns = @JoinColumn(name = "variant_id")
    )
    @Column(name = "public_id")
    private List<String> images = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}
