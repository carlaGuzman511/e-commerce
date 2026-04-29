package com.kiosco.product_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryUpdateRequest {

    private Long id;
    @NotBlank
    @Size(max = 255)
    private String name;

    private String icon;

    @Size(max = 255)
    private String description;

    private Integer orderIndex;
}
