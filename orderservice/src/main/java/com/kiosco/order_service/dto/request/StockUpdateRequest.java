package com.kiosco.order_service.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StockUpdateRequest {
    private Long variantId;

    private Long branchId;

    private int quantity;
}