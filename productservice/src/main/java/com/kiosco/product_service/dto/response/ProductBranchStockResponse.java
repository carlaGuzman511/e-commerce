package com.kiosco.product_service.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductBranchStockResponse {
    private Long branchId;
    private Integer stock;
}