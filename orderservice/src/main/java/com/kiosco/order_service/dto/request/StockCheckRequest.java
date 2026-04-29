package com.kiosco.order_service.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StockCheckRequest {
    private Long branchId;

    private Long variantId;

    private int requested;
}