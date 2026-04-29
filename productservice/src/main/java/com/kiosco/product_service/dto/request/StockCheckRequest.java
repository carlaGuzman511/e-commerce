package com.kiosco.product_service.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockCheckRequest {
    private Long branchId;

    private Long variantId;

    private Long requested;
}