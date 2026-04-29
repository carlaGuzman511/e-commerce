package com.kiosco.order_service.controller;


import com.kiosco.order_service.constants.ApiPath;
import com.kiosco.order_service.constants.Messages;
import com.kiosco.order_service.dto.request.UpdateOrderStatusRequest;
import com.kiosco.order_service.dto.response.ApiResponse;
import com.kiosco.order_service.dto.response.OrderResponse;
import com.kiosco.order_service.service.UpdateOrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping(ApiPath.Order.BASE)
public class UpdateOrderStatusController {
    private final UpdateOrderService updateOrderService;

    public UpdateOrderStatusController(UpdateOrderService updateOrderService) {
        this.updateOrderService = updateOrderService;
    }

    @PutMapping(ApiPath.Order.UPDATE_ORDER_STATUS)
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrderStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateOrderStatusRequest request) {
        OrderResponse orderResponse = updateOrderService.updateStatus(id, request.getOrderStatus());

        ApiResponse<OrderResponse> response = ApiResponse.<OrderResponse>builder()
                .timestamp(Instant.now())
                .status(HttpStatus.OK.value())
                .message(Messages.ORDER_STATUS_UPDATED)
                .data(orderResponse)
                .build();

        return ResponseEntity.ok(response);
    }
}