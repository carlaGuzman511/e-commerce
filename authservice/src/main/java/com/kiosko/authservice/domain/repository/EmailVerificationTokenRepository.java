package com.kiosko.authservice.domain.repository;

import com.kiosko.authservice.domain.entity.EmailVerificationToken;
import com.kiosko.authservice.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, Long> {

    Optional<EmailVerificationToken> findByToken(String token);

    @Query("SELECT evt FROM EmailVerificationToken evt WHERE evt.token = :token AND evt.used = false AND evt.expiresAt > :now")
    Optional<EmailVerificationToken> findValidToken(@Param("token") String token, @Param("now") LocalDateTime now);

    Optional<EmailVerificationToken> findByUser(User user);

    void deleteByUser(User user);

    @Modifying
    @Query("DELETE FROM EmailVerificationToken evt WHERE evt.expiresAt < :now")
    void deleteExpiredTokens(@Param("now") LocalDateTime now);
}