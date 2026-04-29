package com.kiosco.product_service.config;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.media.StringSchema;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

@Component
public class GlobalTenantHeaderCustomizer implements OperationCustomizer {

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        Parameter tenantHeader = new Parameter()
                .in("header")
                .name("X-Tenant-ID")
                .description("Tenant identifier (required for multi-tenant requests)")
                .required(true)
                .schema(new StringSchema());

        if (operation.getParameters() == null) {
            operation.setParameters(new java.util.ArrayList<>());
        }

        boolean alreadyExists = operation.getParameters().stream()
                .anyMatch(p -> "X-Tenant-ID".equals(p.getName()) && "header".equals(p.getIn()));

        if (!alreadyExists) {
            operation.getParameters().add(0, tenantHeader);
        }

        return operation;
    }
}