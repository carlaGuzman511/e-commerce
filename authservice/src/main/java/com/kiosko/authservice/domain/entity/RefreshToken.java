package com.kiosko.authservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = DatabaseConstants.REFRESH_TOKENS_TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = DatabaseConstants.TOKEN_COLUMN, nullable = false, unique = true, length = 500)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DatabaseConstants.USER_ID_COLUMN, nullable = false)
    private User user;

    @Column(name = DatabaseConstants.EXPIRES_AT_COLUMN, nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = DatabaseConstants.REVOKED_COLUMN, nullable = false)
    private Boolean revoked = false;

    @CreationTimestamp
    @Column(name = DatabaseConstants.CREATED_AT_COLUMN, nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiresAt);
    }

    public boolean isValid() {
        return !this.revoked && !this.isExpired();
    }

    public void revoke() {
        this.revoked = true;
    }
}
