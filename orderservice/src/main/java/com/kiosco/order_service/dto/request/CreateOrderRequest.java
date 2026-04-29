package com.kiosco.order_service.dto.request;
import com.kiosco.order_service.domain.ShippingAddress;
import com.kiosco.order_service.enums.DeliveryMethod;
import com.kiosco.order_service.enums.PaymentMethod;
import jakarta.validation.Valid;
import lombok.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
    @NotNull
    private Long userId;

    @NotNull
    private Long branchId;

    @NotNull
    @Valid
    private List<OrderItemRequest> items;

    private ShippingAddress shippingAddress;

    private PaymentMethod paymentMethod;

    private DeliveryMethod deliveryMethod;
}