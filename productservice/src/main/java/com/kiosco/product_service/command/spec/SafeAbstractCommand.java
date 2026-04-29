package com.kiosco.product_service.command.spec;

import com.kiosco.product_service.exeption.CommandException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;

public abstract class SafeAbstractCommand<I, O> implements Command<I, O> {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public O execute(I input) {
        try {
            preExecute(input);

            O output = safeExecute(input);

            postExecute(output);

            return output;
        } catch (
                IllegalArgumentException |
                HttpMessageNotReadableException exception
        ) {
            throw exception;
        } catch (Exception exception) {
            throw new CommandException(exception);
        }
    }

    protected abstract O safeExecute(I input);

    protected void preExecute(I input) {
    }

    protected void postExecute(O output) {
    }
}