package com.example.desingpatterns.service.states;

import com.example.desingpatterns.interfaces.OrderState;
import com.example.desingpatterns.service.OrderContext;

public class DeliveredState implements OrderState {
    @Override
    public void next(OrderContext context) {
        System.out.println("The order is already delivered. there is no a next state.");
    }

    @Override
    public void previous(OrderContext context) {
        context.setState(new ShippedState());
    }

    @Override
    public String getStatus() {
        return "Order delivered.";
    }
}