package com.kiosco.order_service.dto.request;

import com.kiosco.order_service.constants.Messages;
import com.kiosco.order_service.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderStatusRequest {
    @NotNull(message = Messages.ORDER_STATUS_IS_REQUIRED)
    private OrderStatus orderStatus;
}
