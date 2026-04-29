package com.kiosko.gateway_service.config;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r
                        .path("/auth-service/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://auth-service")
                )
                .route("order-service", r -> r
                        .path("/order-service/**")
                        .filters(f -> f.stripPrefix(1)) 
                        .uri("lb://order-service")
                )
                .route("product-service", r -> r
                        .path("/product-service/**")
                        .filters(f -> f.stripPrefix(1)) 
                        .uri("lb://product-service")
                )
                .route("multimedia-service", r -> r
                        .path("/multimedia-service/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://multimedia-service")
                )
                .route("tenant-service", r -> r
                        .path("/tenant-service/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://tenant-service")
                )
                .build();
    }
}
