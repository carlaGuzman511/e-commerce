package com.example.desingpatterns.service.states;

import com.example.desingpatterns.interfaces.OrderState;
import com.example.desingpatterns.service.OrderContext;

public class ShippedState implements OrderState {
    @Override
    public void next(OrderContext context) {
        context.setState(new DeliveredState());
    }

    @Override
    public void previous(OrderContext context) {
        context.setState(new PaidState());
    }

    @Override
    public String getStatus() {
        return "Order shipped.";
    }
}