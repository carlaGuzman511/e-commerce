package com.example.desingpatterns.service;

import com.example.desingpatterns.interfaces.OrderState;
import com.example.desingpatterns.service.states.CreatedState;

public class OrderContext {
    private OrderState state;

    public OrderContext() {
        state = new CreatedState();
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public String getStatus() {
        return state.getStatus();
    }

    public void nextState() {
        state.next(this);
    }

    public void previousState() {
        state.previous(this);
    }
}