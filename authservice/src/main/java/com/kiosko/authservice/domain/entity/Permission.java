package com.kiosko.authservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = DatabaseConstants.PERMISSIONS_TABLE,
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_permission_resource_action", columnNames = {
                        DatabaseConstants.RESOURCE_COLUMN,
                        DatabaseConstants.ACTION_COLUMN
                })
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = DatabaseConstants.RESOURCE_COLUMN, nullable = false, length = 50)
    private String resource;

    @Column(name = DatabaseConstants.ACTION_COLUMN, nullable = false, length = 50)
    private String action;

    @Column(name = DatabaseConstants.DESCRIPTION_COLUMN, length = 255)
    private String description;

    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    @CreationTimestamp
    @Column(name = DatabaseConstants.CREATED_AT_COLUMN, nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public String getFullPermission() {
        return resource + ":" + action;
    }
}
