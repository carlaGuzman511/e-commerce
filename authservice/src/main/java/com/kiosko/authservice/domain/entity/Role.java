package com.kiosko.authservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = DatabaseConstants.ROLES_TABLE,
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_role_name", columnNames = DatabaseConstants.NAME_COLUMN)
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = DatabaseConstants.NAME_COLUMN, nullable = false, unique = true, length = 50)
    private String name;

    @Column(name = DatabaseConstants.DESCRIPTION_COLUMN, length = 255)
    private String description;

    @Column(name = DatabaseConstants.TENANT_ID_COLUMN)
    private String tenantId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = DatabaseConstants.ROLE_ID_COLUMN),
            inverseJoinColumns = @JoinColumn(name = DatabaseConstants.PERMISSION_ID_COLUMN)
    )
    @Builder.Default
    private Set<Permission> permissions = new HashSet<>();

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<User> users = new HashSet<>();

    @CreationTimestamp
    @Column(name = DatabaseConstants.CREATED_AT_COLUMN, nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = DatabaseConstants.UPDATED_AT_COLUMN)
    private LocalDateTime updatedAt;
}
