package com.kiosco.product_service.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductResponse {
    private Long id;

    private String name;

    private String brand;

    private String description;

    private String technicalDetails;

    private Long categoryId;

    private String status;

    private Integer minStock;

    private List<ProductVariantResponse> variants;
}