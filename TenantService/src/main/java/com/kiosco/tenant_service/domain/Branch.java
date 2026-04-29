package com.kiosco.tenant_service.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table( name = "branch",
        schema = "dbo",
        indexes = {
                @Index(name = "idx_store_branch_tenant", columnList = "tenant_id")
        })
public class Branch extends Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Embedded
    private Address address;

    @Column(name = "manager_name")
    private String managerName;

    @Column(name = "manager_phone")
    private String managerPhone;

    @Column(name = "opening_hours")
    private String openingHours;

    @Column(name = "closing_hours")
    private String closingHours;
}