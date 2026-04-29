package com.kiosco.order_service.dto.response;

import com.kiosco.order_service.domain.OrderItem;
import com.kiosco.order_service.domain.ShippingAddress;
import com.kiosco.order_service.domain.Tenant;
import com.kiosco.order_service.enums.DeliveryMethod;
import com.kiosco.order_service.enums.OrderStatus;
import com.kiosco.order_service.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class OrderResponse extends Tenant {
    private Long id;
    private OrderStatus orderStatus;
    private Long userId;
    private DeliveryMethod deliveryMethod;
    private PaymentMethod paymentMethod;
    private BigDecimal total;
    private BigDecimal discounts;
    private List<OrderItemResponse> items;
    private ShippingAddress shippingAddress;
}