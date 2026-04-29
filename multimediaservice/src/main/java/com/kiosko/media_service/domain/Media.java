package com.kiosko.media_service.domain;

import com.kiosko.media_service.dto.TenantEntity;
import com.kiosko.media_service.enums.OwnerType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "media", indexes = {@Index(name = "idx_media_tenant", columnList = "tenant_id")})
public class Media extends TenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "public_id", unique = true)
    private String publicId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private OwnerType ownerType;

    private Integer orderIndex;

    @Column(length = 100)
    private String folder;

}
