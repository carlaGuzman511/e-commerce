package com.kiosco.product_service.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockUpdateRequest {
    private Long variantId;

    private Long branchId;

    private Integer quantity;
}
