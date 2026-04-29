package com.kiosco.order_service.dto.request;

import com.kiosco.order_service.domain.ShippingAddress;
import com.kiosco.order_service.enums.DeliveryMethod;
import com.kiosco.order_service.enums.PaymentMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderRequest {
    @NotNull
    private Long userId;

    @NotNull
    private Long branchId;

    @NotNull
    private Long variantId;

    @NotNull
    @Valid
    private List<OrderItemRequest> items;

    private ShippingAddress shippingAddress;

    private PaymentMethod paymentMethod;

    private DeliveryMethod deliveryMethod;
}