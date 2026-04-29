package com.kiosco.order_service.controller;

import com.kiosco.order_service.constants.ApiPath;
import com.kiosco.order_service.constants.Messages;
import com.kiosco.order_service.dto.response.ApiResponse;
import com.kiosco.order_service.dto.response.OrderResponse;
import com.kiosco.order_service.enums.OrderStatus;
import com.kiosco.order_service.service.GetOrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

@RestController
@RequestMapping(ApiPath.Order.BASE)
public class GetOrderController {
    private final GetOrderService getOrderService;

    public GetOrderController(GetOrderService getOrderService) {
        this.getOrderService = getOrderService;
    }

    @GetMapping(ApiPath.Order.BY_ID)
    public ResponseEntity<ApiResponse<OrderResponse>> getOrder(
            @PathVariable Long id) {
        OrderResponse orderResponse = getOrderService.getById(id);

        ApiResponse<OrderResponse> response = ApiResponse.<OrderResponse>builder()
                .timestamp(Instant.now())
                .status(HttpStatus.OK.value())
                .message(Messages.ORDER_RETRIEVED)
                .data(orderResponse)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<OrderResponse>>> listOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) OrderStatus orderStatus,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
        Page<OrderResponse> pagedOrderResponse = getOrderService.getAll(
                PageRequest.of(page, size),
                orderStatus,
                userId,
                startDate != null ? startDate.atStartOfDay().toInstant(ZoneOffset.UTC) : null,
                endDate != null ? endDate.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC) : null);

        ApiResponse<Page<OrderResponse>> response = ApiResponse.<Page<OrderResponse>>builder()
                .timestamp(Instant.now())
                .status(HttpStatus.OK.value())
                .message(Messages.ORDERS_RETRIEVED)
                .data(pagedOrderResponse)
                .build();

        return ResponseEntity.ok(response);
    }
}