package com.kiosko.authservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtUtils {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.access-token-expiration}")
    private Long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private Long refreshTokenExpiration;

    public String generateAccessToken(CustomUserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("userId", userDetails.getUser().getId());
        claims.put("tenantId", userDetails.getUser().getTenantId());

        Set<String> roles = userDetails.getAuthorities().stream()
                .filter(auth -> auth.getAuthority().startsWith("ROLE_"))
                .map(auth -> auth.getAuthority().replace("ROLE_", ""))
                .collect(Collectors.toSet());
        claims.put("roles", roles);

        Set<String> permissions = userDetails.getAuthorities().stream()
                .filter(auth -> !auth.getAuthority().startsWith("ROLE_"))
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        claims.put("permissions", permissions);

        List<String> authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("authorities", authorities);

        return buildToken(claims, userDetails.getUsername(), accessTokenExpiration);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return buildToken(claims, userDetails.getUsername(), refreshTokenExpiration);
    }

    private String buildToken(Map<String, Object> extraClaims, String subject, Long expiration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .claims(extraClaims)
                .subject(subject)
                .issuer("AuthService")
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Long extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        Object userIdObj = claims.get("userId");

        if (userIdObj instanceof Integer) {
            return ((Integer) userIdObj).longValue();
        } else if (userIdObj instanceof Long) {
            return (Long) userIdObj;
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public Set<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        List<String> roles = claims.get("roles", List.class);
        return roles != null ? new HashSet<>(roles) : new HashSet<>();
    }

    @SuppressWarnings("unchecked")
    public Set<String> extractPermissions(String token) {
        Claims claims = extractAllClaims(token);
        List<String> permissions = claims.get("permissions", List.class);
        return permissions != null ? new HashSet<>(permissions) : new HashSet<>();
    }

    @SuppressWarnings("unchecked")
    public List<String> extractAuthorities(String token) {
        Claims claims = extractAllClaims(token);
        List<String> authorities = claims.get("authorities", List.class);
        return authorities != null ? authorities : new ArrayList<>();
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Map<String, Object> decodeTokenInfo(String token) {
        Claims claims = extractAllClaims(token);

        Map<String, Object> tokenInfo = new HashMap<>();
        tokenInfo.put("subject", claims.getSubject());
        tokenInfo.put("issuer", claims.getIssuer());
        tokenInfo.put("issuedAt", claims.getIssuedAt());
        tokenInfo.put("expiration", claims.getExpiration());

        return tokenInfo;
    }
}