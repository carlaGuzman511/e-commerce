package com.kiosco.order_service.dto.mapper;

import com.kiosco.order_service.domain.Order;
import com.kiosco.order_service.domain.OrderItem;
import com.kiosco.order_service.dto.request.CreateOrderRequest;
import com.kiosco.order_service.dto.request.OrderItemRequest;
import com.kiosco.order_service.dto.request.UpdateOrderRequest;
import com.kiosco.order_service.dto.response.OrderItemResponse;
import com.kiosco.order_service.dto.response.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "orderStatus", ignore = true)
    @Mapping(target = "items", source = "items")
    Order toEntity(CreateOrderRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "orderStatus", ignore = true)
    @Mapping(target = "items", source = "items")
    Order toEntity(UpdateOrderRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "total", source = ".", qualifiedByName = "calculateTotal")
    OrderItem toEntity(OrderItemRequest orderItemRequest);

    @Mapping(target = "orderId", expression = "java(orderItem.getOrder() != null ? orderItem.getOrder().getId() : null)")
    OrderItemResponse toOrderItemResponse(OrderItem orderItem);

    @Mapping(target = "items", expression = "java(order.getItems().stream().map(this::toOrderItemResponse).toList())")
    OrderResponse toOrderResponse(Order order);

    default Page<OrderResponse> toResponsePage(Page<Order> orders) {
        return orders.map(this::toOrderResponse);
    }

    @Named("calculateTotal")
    default BigDecimal calculateTotal(OrderItemRequest orderItemRequest) {
        if (orderItemRequest == null ||
                orderItemRequest.getPrice() == null ||
                orderItemRequest.getQuantity() == 0) {
            return BigDecimal.ZERO.setScale(2);
        }

        BigDecimal qty = BigDecimal.valueOf(orderItemRequest.getQuantity());
        BigDecimal price = orderItemRequest.getPrice();
        BigDecimal subtotal = price.multiply(qty);

        BigDecimal discountPerUnit = orderItemRequest.getDiscount() == null
                ? BigDecimal.ZERO
                : orderItemRequest.getDiscount();

        BigDecimal discountTotal = discountPerUnit.multiply(qty);
        BigDecimal total = subtotal.subtract(discountTotal);

        if (total.compareTo(BigDecimal.ZERO) < 0) {
            total = BigDecimal.ZERO;
        }

        return total.setScale(2, java.math.RoundingMode.HALF_UP);
    }
}