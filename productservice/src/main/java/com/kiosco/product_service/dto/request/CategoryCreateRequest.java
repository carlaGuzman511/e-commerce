package com.kiosco.product_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryCreateRequest {
    @NotBlank
    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String description;

    private String icon;

    private Integer orderIndex;
}
