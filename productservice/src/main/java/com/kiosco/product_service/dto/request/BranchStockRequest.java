package com.kiosco.product_service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BranchStockRequest {

    @NotNull
    private Long branchId;

    @NotNull
    private Integer stock;
}
