package com.kiosko.media_service.dto;

import com.kiosko.media_service.config.AuditContext;
import com.kiosko.media_service.config.TenantContext;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Data
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
public abstract class TenantEntity {

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        this.tenantId = TenantContext.get();
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();

        this.createdBy = AuditContext.getCurrentUser();
        this.updatedBy = AuditContext.getCurrentUser();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
        this.updatedBy = AuditContext.getCurrentUser();
    }
}