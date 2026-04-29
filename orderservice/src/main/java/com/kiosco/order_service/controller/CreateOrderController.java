package com.kiosco.order_service.controller;

import com.kiosco.order_service.constants.ApiPath;
import com.kiosco.order_service.constants.Messages;
import com.kiosco.order_service.dto.request.CreateOrderRequest;
import com.kiosco.order_service.dto.response.ApiResponse;
import com.kiosco.order_service.dto.response.OrderResponse;
import com.kiosco.order_service.service.CreateOrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping(ApiPath.Order.BASE)
public class CreateOrderController {
        private final CreateOrderService createOrderService;

        public CreateOrderController(CreateOrderService createOrderService) {
            this.createOrderService = createOrderService;
        }

        @PostMapping
        public ResponseEntity<ApiResponse<OrderResponse>> createOrder(
                @Valid @RequestBody CreateOrderRequest createOrderRequest) {
            OrderResponse orderResponse = createOrderService.create(createOrderRequest);

            ApiResponse<OrderResponse> response = ApiResponse.<OrderResponse>builder()
                    .timestamp(Instant.now())
                    .status(HttpStatus.CREATED.value())
                    .message(Messages.ORDER_CREATED)
                    .data(orderResponse)
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
}