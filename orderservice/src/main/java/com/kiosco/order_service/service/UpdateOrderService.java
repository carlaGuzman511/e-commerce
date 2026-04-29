package com.kiosco.order_service.service;

import com.kiosco.order_service.config.TenantContext;
import com.kiosco.order_service.constants.Messages;
import com.kiosco.order_service.domain.Order;
import com.kiosco.order_service.dto.mapper.OrderMapper;
import com.kiosco.order_service.dto.response.OrderResponse;
import com.kiosco.order_service.enums.OrderStatus;
import com.kiosco.order_service.exception.InvalidOrderStateException;
import com.kiosco.order_service.exception.ResourceNotFoundException;
import com.kiosco.order_service.repository.OrderRepository;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UpdateOrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public UpdateOrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderResponse updateStatus(Long orderId, OrderStatus newStatus) {
        String tenantId = TenantContext.getCurrentTenant();

        Order order = orderRepository.findByIdAndTenantId(orderId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(Messages.ORDER_NOT_FOUND + orderId));

        if (OrderStatus.CANCELLED.equals(order.getOrderStatus()) || OrderStatus.PAID.equals(order.getOrderStatus())) {
            throw new InvalidOrderStateException(Messages.INVALID_ORDER_STATUS_CHANGE + order.getOrderStatus());
        }

        order.setOrderStatus(newStatus);
        order.setUpdatedAt(LocalDateTime.now());
        Order saved = orderRepository.save(order);

        OrderResponse orderResponse = orderMapper.toOrderResponse(saved);

        return orderResponse;
    }
}