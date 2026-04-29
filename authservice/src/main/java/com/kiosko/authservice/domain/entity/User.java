package com.kiosko.authservice.domain.entity;

import com.kiosko.authservice.domain.enums.AuthProvider;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = DatabaseConstants.USERS_TABLE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = DatabaseConstants.EMAIL_COLUMN, unique = true, nullable = false, length = 50)
    private String email;

    @Column(name = DatabaseConstants.USERNAME_COLUMN, unique = true, nullable = false, length = 100)
    private String username;

    @Column(name = DatabaseConstants.PASSWORD_COLUMN, nullable = false, length = 255)
    private String password;

    @Column(name = DatabaseConstants.IMAGE_COLUMN, length = 500)
    private String image;

    @Column(name = DatabaseConstants.ACTIVE_COLUMN, nullable = false)
    @Builder.Default
    private Boolean active = true;

    @Column(name = DatabaseConstants.TENANT_ID_COLUMN)
    private String tenantId;

    @Column(name = DatabaseConstants.EMAIL_VERIFIED_COLUMN, nullable = false)
    @Builder.Default
    private Boolean emailVerified = false;

    @Enumerated(EnumType.STRING)
    @Column(name = DatabaseConstants.AUTH_PROVIDER_COLUMN, nullable = false, length = 20)
    @Builder.Default
    private AuthProvider authProvider = AuthProvider.local;

    @Column(name = DatabaseConstants.PROVIDER_ID_COLUMN, length = 100)
    private String providerId;

    @Column(name = DatabaseConstants.FAILED_LOGIN_ATTEMPTS_COLUMN, nullable = false)
    @Builder.Default
    private Integer failedLoginAttempts = 0;

    @Column(name = DatabaseConstants.LAST_LOGIN_AT_COLUMN)
    private LocalDateTime lastLoginAt;

    @Column(name = DatabaseConstants.LAST_LOGIN_IP_COLUMN, length = 45)
    private String lastLoginIp;

    @CreationTimestamp
    @Column(name = DatabaseConstants.CREATED_AT_COLUMN, nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = DatabaseConstants.UPDATED_AT_COLUMN, nullable = false)
    private LocalDateTime updatedAt;

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = DatabaseConstants.USER_ROLE_TABLE,
            joinColumns = @JoinColumn(name = DatabaseConstants.USER_ID_COLUMN),
            inverseJoinColumns = @JoinColumn(name = DatabaseConstants.ROLE_ID_COLUMN)
    )
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = DatabaseConstants.USER_PERMISSION_TABLE,
            joinColumns = @JoinColumn(name = DatabaseConstants.USER_ID_COLUMN),
            inverseJoinColumns = @JoinColumn(name = DatabaseConstants.PERMISSION_ID_COLUMN)
    )
    @Builder.Default
    private Set<Permission> permissions = new HashSet<>();

    public void incrementFailedAttempts() {
        this.failedLoginAttempts++;
    }

    public void resetFailedAttempts() {
        this.failedLoginAttempts = 0;
    }

    public void lock() {
        this.active = false;
    }

    public void unlock() {
        this.active = true;
    }

    public boolean isLocked() {
        return !this.active;
    }
}
