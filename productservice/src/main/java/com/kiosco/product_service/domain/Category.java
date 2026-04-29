package com.kiosco.product_service.domain;

import com.kiosco.product_service.dto.TenantEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "category", schema = "dbo")
public class Category extends TenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String icon;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(name = "description", length = 255)
    private String description;

    private Integer orderIndex;
}
