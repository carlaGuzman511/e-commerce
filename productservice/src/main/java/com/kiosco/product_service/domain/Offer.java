package com.kiosco.product_service.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;

@Entity()
@Getter
@Setter
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tenantId;

    private String description;

    private OffsetDateTime expirationDate;

    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL)
    private List<ProductOffer> productOffers;

    private Instant createdAt;

    private Instant updatedAt;
}
