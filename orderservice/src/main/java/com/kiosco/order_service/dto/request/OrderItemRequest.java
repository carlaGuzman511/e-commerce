package com.kiosco.order_service.dto.request;

import com.kiosco.order_service.constants.Messages;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequest {
    @NotNull(message = Messages.PRODUCT_ID_SHOULD_NOT_BE_NULL)
    private Long productId;

    @NotNull(message = Messages.QUANTITY_SHOULD_NOT_BE_NULL)
    @PositiveOrZero(message = Messages.QUANTITY_SHOULD_NOT_BE_NEGATIVE)
    private int quantity;

    @NotNull(message = Messages.PRICE_SHOULD_NOT_BE_NULL)
    @DecimalMin(value = "0.00", inclusive = true, message = Messages.PRICE_SHOULD_NOT_BE_NEGATIVE)
    private BigDecimal price;

    @NotNull(message = Messages.DISCOUNT_SHOULD_NOT_BE_NULL)
    @DecimalMin(value = "0.00", inclusive = true, message = Messages.DISCOUNT_SHOULD_NOT_BE_NEGATIVE)
    private BigDecimal discount;

    @NotNull(message = Messages.VARIANT_ID_REQUIRED)
    private Long variantId;
}