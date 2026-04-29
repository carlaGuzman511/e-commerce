package com.example.desingpatterns.interfaces;

import com.example.desingpatterns.service.OrderContext;

public interface OrderState {
    void next(OrderContext context);
    void previous(OrderContext context);
    String getStatus();
}