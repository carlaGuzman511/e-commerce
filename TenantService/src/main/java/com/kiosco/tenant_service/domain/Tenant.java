package com.kiosco.tenant_service.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@SuperBuilder
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class Tenant {
    protected Tenant() {}

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    protected Tenant(String tenantId) {
        this.tenantId = tenantId;
    }
}
