package com.example.desingpatterns.service.states;

import com.example.desingpatterns.interfaces.OrderState;
import com.example.desingpatterns.service.OrderContext;

public class CreatedState implements OrderState {

    @Override
    public void next(OrderContext context) {
        context.setState(new PaidState());
    }

    @Override
    public void previous(OrderContext context) {
        System.out.println("The order is in its initial state, No previous state.");
    }

    @Override
    public String getStatus() {
        return "Order Created.";
    }
}