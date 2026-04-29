package com.example.desingpatterns.service.states;

import com.example.desingpatterns.interfaces.OrderState;
import com.example.desingpatterns.service.OrderContext;

public class PaidState implements OrderState {
    @Override
    public void next(OrderContext context) {
        context.setState(new ShippedState());
    }

    @Override
    public void previous(OrderContext context) {
        context.setState(new CreatedState());
    }

    @Override
    public String getStatus() {
        return "Order paid.";
    }
}
