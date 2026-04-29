package com.kiosco.product_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ProductVariantUpdateRequest {
    @NotBlank
    private Long id;

    @NotBlank
    private String productCode;

    private String description;

    @NotNull
    private BigDecimal price;

    @NotNull
    private List<BranchStockRequest> branchStocks;

    private Map<String, Object> attributes;
}
