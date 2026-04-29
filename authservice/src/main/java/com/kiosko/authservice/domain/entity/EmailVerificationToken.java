package com.kiosko.authservice.domain.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = DatabaseConstants.EMAIL_VERIFICATION_TOKENS_TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailVerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = DatabaseConstants.TOKEN_COLUMN, nullable = false, unique = true, length = 255)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DatabaseConstants.USER_ID_COLUMN, nullable = false)
    private User user;

    @Column(name = DatabaseConstants.EXPIRES_AT_COLUMN, nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = DatabaseConstants.USED_COLUMN, nullable = false)
    private Boolean used = false;

    @CreationTimestamp
    @Column(name = DatabaseConstants.CREATED_AT_COLUMN, nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiresAt);
    }

    public boolean isValid() {
        return !this.used && !this.isExpired();
    }

    public void markAsUsed() {
        this.used = true;
    }
}
