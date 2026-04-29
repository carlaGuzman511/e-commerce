package com.kiosco.tenant_service.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "log_history", schema = "dbo")
public class LogHistory extends Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "table_name", nullable = false)
    private String tableName;

    @Column(name = "record_id", nullable = false)
    private Long recordId;

    @Column(name = "previous_value", columnDefinition = "TEXT")
    private String previousValue;

    @Column(name = "current_value", columnDefinition = "TEXT")
    private String currentValue;

    @Column(name = "user_id")
    private Long userId;
}
