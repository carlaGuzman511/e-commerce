package com.kiosco.product_service.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductVariantResponse {

    private Long id;

    private String productCode;

    private String description;

    private BigDecimal price;

    private String attributesJson;

    private List<ProductBranchStockResponse> branchStocks;

    private List<MediaResponse> images;
}
