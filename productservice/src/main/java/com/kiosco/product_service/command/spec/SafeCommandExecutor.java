package com.kiosco.product_service.command.spec;

import org.springframework.stereotype.Component;

@Component
public class SafeCommandExecutor {
    public <I, O> O execute(Command<I, O> command, I input) {
        return command.execute(input);
    }
}
