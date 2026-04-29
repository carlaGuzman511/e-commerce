package com.kiosco.product_service.command.spec;

public interface Command<I, O> {
    O execute(I input);
}
