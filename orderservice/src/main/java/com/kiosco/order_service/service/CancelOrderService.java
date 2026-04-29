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

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CancelOrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final StockService stockService;

    public CancelOrderService(
            OrderRepository orderRepository,
            OrderMapper orderMapper,
            StockService stockService) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.stockService = stockService;
    }

    @Transactional
    public OrderResponse cancelOrder(Long orderId) {
        String tenantId = TenantContext.getCurrentTenant();

        Order order = orderRepository.findByIdAndTenantId(orderId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(Messages.ORDER_NOT_FOUND + orderId));

        if (!OrderStatus.PENDING.equals(order.getOrderStatus())) {
            throw new InvalidOrderStateException(Messages.INVALID_ORDER_STATUS_CHANGE + order.getOrderStatus());
        }

        stockService.restoreStock(order);

        order.setOrderStatus(OrderStatus.CANCELLED);

        Order saved = orderRepository.save(order);
        return orderMapper.toOrderResponse(saved);
    }
}