package com.kiosko.gateway_service.config;

import com.kiosko.gateway_service.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.WeakKeyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtAuthFilter implements GlobalFilter {

    private final List<String> publicRoutes = List.of(
            "/api/v1/products/**",
            "/api/v1/categories/**"
    );

    private final List<String> authRoutes = List.of(
            "/api/v1/auth/login",
            "/api/v1/auth/register"
    );

    private final Map<String, List<Role>> protectedRoutes = Map.ofEntries(
            Map.entry("/api/v1/stores/**", List.of(Role.SUPER_ADMIN)),
            Map.entry("/api/v1/branches/**", List.of(Role.TENANT_ADMIN)),
            Map.entry("/api/v1/products/**", List.of(Role.TENANT_ADMIN)),
            Map.entry("/api/v1/orders/**", List.of(Role.TENANT_ADMIN, Role.CLIENT, Role.SELLER))
    );

    private final Key signingKey;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public JwtAuthFilter(@Value("${jwt.secret-key:}") String jwtSecretPlain) {
        this.signingKey = buildSigningKey(jwtSecretPlain);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        if (matches(path, authRoutes)) {
            return chain.filter(exchange);
        }

        if (matches(path, publicRoutes)) {
            String tenantId = exchange.getRequest().getHeaders().getFirst("X-tenant-Id");

            if (tenantId == null || tenantId.isBlank()) {
                return badRequest(exchange, "Missing header: X-tenant-Id");
            }

            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange, "Missing Authorization header");
        }

        String token = authHeader.substring("Bearer ".length()).trim();

        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return unauthorized(exchange, "Invalid JWT: " + e.getMessage());
        }

        List<Role> roles = extractRoles(claims);

        if (!isAuthorized(path, roles)) {
            return unauthorized(exchange, "Access denied");
        }

        ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                .header("X-user-id", String.valueOf(claims.get("userId")))
                .header("X-tenant-id", String.valueOf(claims.get("tenantId")))
                .build();

        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }

    private boolean matches(String path, List<String> patterns) {
        return patterns.stream().anyMatch(p -> pathMatcher.match(p, path));
    }

    private boolean isAuthorized(String path, List<Role> roles) {
        return protectedRoutes.entrySet().stream().anyMatch(entry ->
                pathMatcher.match(entry.getKey(), path) &&
                        roles.stream().anyMatch(entry.getValue()::contains)
        );
    }

    private List<Role> extractRoles(Claims claims) {
        List<String> raw = claims.get("roles", List.class);
        if (raw == null) return List.of();
        return raw.stream().map(Role::valueOf).collect(Collectors.toList());
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        return writeError(exchange, HttpStatus.UNAUTHORIZED, message);
    }

    private Mono<Void> badRequest(ServerWebExchange exchange, String message) {
        return writeError(exchange, HttpStatus.BAD_REQUEST, message);
    }

    private Mono<Void> writeError(ServerWebExchange exchange, HttpStatus status, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(bytes)));
    }

    private Key buildSigningKey(String secret) {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length < 64) {
            throw new WeakKeyException("JWT key too weak (must be >= 512 bits)");
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
