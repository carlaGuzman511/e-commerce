package com.kiosco.order_service.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table( name = "order_items",
        schema = "dbo",
        indexes = {
            @Index(name = "idx_order_items_tenant", columnList = "tenant_id")
        })
public class OrderItem extends Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "variant_id", nullable = false)
    private Long variantId;
    
    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "total", nullable = false)
    private BigDecimal total;

    @Column(name = "discount", nullable = true)
    private  BigDecimal discount;

    public void initialize(String tenantId, Order order) {
        this.tenantId = tenantId;
        this.order = order;
    }
}