package com.example.desingpatterns.controllers;

import com.example.desingpatterns.service.OrderContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private OrderContext orderContext = new OrderContext();

    @GetMapping("/status")
    public String getStatus() {

        return orderContext.getStatus();
    }

    @PostMapping("/next")
    public String next() {
        orderContext.nextState();

        return orderContext.getStatus();
    }

    @PostMapping("/previous")
    public String previous() {
        orderContext.previousState();

        return orderContext.getStatus();
    }
}