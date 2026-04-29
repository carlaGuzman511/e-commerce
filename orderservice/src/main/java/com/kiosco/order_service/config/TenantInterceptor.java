package com.kiosco.order_service.config;

import com.kiosco.order_service.constants.Messages;
import com.kiosco.order_service.exception.InvalidTenantException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TenantInterceptor implements HandlerInterceptor {

    @Value("${app.tenant-header:X-Tenant-ID}")
    private String tenantHeader;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String tenantId = request.getHeader(tenantHeader);
        if (tenantId == null || tenantId.isBlank()) {
            throw new InvalidTenantException(Messages.INVALID_TENANT_ID);
        }

        TenantContext.setCurrentTenant(tenantId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        TenantContext.clear();
    }
}