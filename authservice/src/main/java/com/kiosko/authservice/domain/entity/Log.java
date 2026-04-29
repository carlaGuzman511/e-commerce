package com.kiosko.authservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = DatabaseConstants.LOG_TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = DatabaseConstants.RECORD_ID_COLUMN)
    private Long recordId;

    @Column(name = DatabaseConstants.TABLE_NAME_COLUMN, length = 255)
    private String tableName;

    @Column(name = DatabaseConstants.PREVIOUS_VALUE_COLUMN, columnDefinition = "TEXT")
    private String previousValue;

    @Column(name = DatabaseConstants.CURRENT_VALUE_COLUMN, columnDefinition = "TEXT")
    private String currentValue;

    @Column(name = DatabaseConstants.ACTION_LOG_COLUMN, nullable = false, length = 50)
    private String action;

    @Column(name = DatabaseConstants.DESCRIPTION_LOG_COLUMN, columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DatabaseConstants.USER_ID_COLUMN, nullable = false)
    private User user;

    @Column(name = DatabaseConstants.IP_ADDRESS_COLUMN, length = 45)
    private String ipAddress;

    @Column(name = DatabaseConstants.USER_AGENT_COLUMN, columnDefinition = "TEXT")
    private String userAgent;

    @Column(name = DatabaseConstants.TENANT_ID_COLUMN)
    private String tenantId;

    @CreationTimestamp
    @Column(name = DatabaseConstants.CREATED_AT_COLUMN, nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = DatabaseConstants.UPDATED_AT_COLUMN, nullable = false)
    private LocalDateTime updatedAt;
}
