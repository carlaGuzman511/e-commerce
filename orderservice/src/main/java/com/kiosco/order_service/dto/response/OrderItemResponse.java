package com.kiosco.order_service.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemResponse {
    private Long id;

    private Long orderId;

    private Long productId;

    private Long variantId;

    private Integer quantity;

    private BigDecimal price;

    private BigDecimal total;

    private  BigDecimal discount;
}
