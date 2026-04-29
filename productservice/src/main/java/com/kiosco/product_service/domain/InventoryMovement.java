package com.kiosco.product_service.domain;

import com.kiosco.product_service.dto.TenantEntity;
import com.kiosco.product_service.enums.MovementReason;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "inventory_movement", schema = "dbo")
public class InventoryMovement extends TenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id")
    private ProductVariant variant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    private Integer quantityChange;

    private MovementReason reason;

    @Column(name = "compensated")
    private Boolean compensated = false;
}
