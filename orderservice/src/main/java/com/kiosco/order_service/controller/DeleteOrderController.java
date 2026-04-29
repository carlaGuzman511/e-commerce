package com.kiosco.order_service.controller;

import com.kiosco.order_service.constants.ApiPath;
import com.kiosco.order_service.constants.Messages;
import com.kiosco.order_service.dto.response.ApiResponse;
import com.kiosco.order_service.dto.response.OrderResponse;
import com.kiosco.order_service.service.CancelOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping(ApiPath.Order.BASE)
public class DeleteOrderController {
    private final CancelOrderService cancelOrderService;

    public DeleteOrderController(CancelOrderService cancelOrderService) {
        this.cancelOrderService = cancelOrderService;
    }

   @PostMapping(ApiPath.Order.CANCEL_ORDER)
    public ResponseEntity<ApiResponse<OrderResponse>> cancelOrder(
            @PathVariable Long id) {
        OrderResponse orderResponse = cancelOrderService.cancelOrder(id);

        ApiResponse<OrderResponse> response = ApiResponse.<OrderResponse>builder()
                .timestamp(Instant.now())
                .status(HttpStatus.OK.value())
                .message(Messages.ORDER_CANCELED)
                .data(orderResponse)
                .build();

        return ResponseEntity.ok(response);
    }
}