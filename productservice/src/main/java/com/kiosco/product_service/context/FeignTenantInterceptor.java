package com.kiosco.product_service.context;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignTenantInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        String tenant = TenantContext.get();
        if (tenant != null && !tenant.isBlank()) {
            template.header(TenantFilter.TENANT_HEADER, tenant);
        }
    }
}
