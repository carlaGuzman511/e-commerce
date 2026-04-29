package com.kiosco.order_service.service;

import com.kiosco.order_service.config.TenantContext;
import com.kiosco.order_service.domain.Order;
import com.kiosco.order_service.dto.request.CreateOrderRequest;
import com.kiosco.order_service.dto.response.OrderResponse;
import com.kiosco.order_service.enums.OrderStatus;
import com.kiosco.order_service.interfaces.generics.ICreate;
import com.kiosco.order_service.dto.mapper.OrderMapper;
import com.kiosco.order_service.repository.OrderRepository;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CreateOrderService implements ICreate<OrderResponse, CreateOrderRequest> {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderCalculator orderCalculator;
    private final StockService stockService;

    public CreateOrderService(
            OrderRepository orderRepository,
            OrderMapper orderMapper,
            OrderCalculator orderCalculator,
            StockService stockService) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.orderCalculator = orderCalculator;
        this.stockService = stockService;
    }

    @Transactional
    @Override
    public OrderResponse create(CreateOrderRequest req) {
        String tenantId = TenantContext.getCurrentTenant();

        stockService.validateStock(req);

        Order order = orderMapper.toEntity(req);
        order.initialize(tenantId, OrderStatus.PENDING);

        orderCalculator.calculateTotals(order);

        stockService.reserveStock(req);

        Order saved = orderRepository.save(order);

        return orderMapper.toOrderResponse(saved);
    }
}