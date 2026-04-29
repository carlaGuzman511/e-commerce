package com.kiosko.media_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TenantFilter extends OncePerRequestFilter {

    public static final String TENANT_HEADER = "X-Tenant-Id";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger")
                || path.startsWith("/swagger-ui")) {
            chain.doFilter(request, response);
            return;
        }

        String tenantId = request.getHeader(TENANT_HEADER);

        if (tenantId == null || tenantId.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");

            Map<String, Object> body = Map.of(
                    "timestamp", Instant.now().toString(),
                    "status", 400,
                    "error", "Bad Request",
                    "message", "Missing header: " + TENANT_HEADER,
                    "path", request.getRequestURI()
            );

            new ObjectMapper().writeValue(response.getOutputStream(), body);
            return;
        }

        try {
            TenantContext.set(tenantId);
            chain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }
}
