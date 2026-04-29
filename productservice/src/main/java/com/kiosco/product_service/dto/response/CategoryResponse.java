package com.kiosco.product_service.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class CategoryResponse {
    private Long id;

    private String tenantId;

    private String name;

    private String icon;

    private String description;

    private Instant createdAt;

    private Instant updatedAt;
}
