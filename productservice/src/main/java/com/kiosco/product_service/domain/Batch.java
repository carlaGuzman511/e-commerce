package com.kiosco.product_service.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "batch", schema = "dbo")
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long storeId;

    private BigDecimal cost;

    private Instant expirationDate;

    private Instant createdAt;

    private Instant updatedAt;
}
