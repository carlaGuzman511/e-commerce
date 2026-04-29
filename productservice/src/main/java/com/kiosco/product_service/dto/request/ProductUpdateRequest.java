package com.kiosco.product_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductUpdateRequest {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String brand;

    private String description;

    private String technicalDetails;

    @NotNull
    private Long categoryId;

    private Integer minStock;

    @NotNull
    private List<ProductVariantUpdateRequest> variants;
}
