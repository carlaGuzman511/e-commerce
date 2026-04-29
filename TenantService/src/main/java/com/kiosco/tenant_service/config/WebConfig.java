package com.kiosco.tenant_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final TenantInterceptor tenantInterceptor;

    @Autowired
    public WebConfig(TenantInterceptor tenantInterceptor) {
        this.tenantInterceptor = tenantInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tenantInterceptor)
                .addPathPatterns("/api/v1/stores/**")
                .addPathPatterns("/api/v1/stores/*/branches/**");
    }
}
