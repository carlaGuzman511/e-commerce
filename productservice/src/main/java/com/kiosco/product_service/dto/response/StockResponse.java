package com.kiosco.product_service.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockResponse {
    private Boolean available;

    private Long stock;

    private Long requested;

    private Integer branchId;

    private Long productId;

    private Long variantId;
}
