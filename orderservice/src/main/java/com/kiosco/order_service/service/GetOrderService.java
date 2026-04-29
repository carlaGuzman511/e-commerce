package com.kiosco.order_service.service;

import com.kiosco.order_service.config.TenantContext;
import com.kiosco.order_service.constants.Messages;
import com.kiosco.order_service.domain.Order;
import com.kiosco.order_service.dto.mapper.OrderMapper;
import com.kiosco.order_service.dto.response.OrderResponse;
import com.kiosco.order_service.enums.OrderStatus;
import com.kiosco.order_service.exception.ResourceNotFoundException;
import com.kiosco.order_service.interfaces.generics.IRead;
import com.kiosco.order_service.repository.OrderRepository;
import com.kiosco.order_service.repository.specification.OrderSpecifications;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GetOrderService implements IRead<OrderResponse, Long> {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public GetOrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderResponse getById(Long id) {
        String tenantId = TenantContext.getCurrentTenant();
        Order order = orderRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(Messages.ORDER_NOT_FOUND + id));
        OrderResponse orderResponse = orderMapper.toOrderResponse(order);

        return orderResponse;
    }

    public Page<OrderResponse> getAll(Pageable pageable, OrderStatus orderStatus, Long userId, Instant startDate, Instant endDate) {
        String tenantId = TenantContext.getCurrentTenant();

        Specification<Order> spec = Specification
                .where(OrderSpecifications.hasTenantId(tenantId))
                .and(OrderSpecifications.hasUserId(userId))
                .and(OrderSpecifications.hasStatus(orderStatus))
                .and(OrderSpecifications.createdBetween(startDate, endDate));

        Page<Order> orders = orderRepository.findAll(spec, pageable);

        Page<OrderResponse> response = orderMapper.toResponsePage(orders);

        return response;
    }
}