package com.kiosco.order_service.exception;

public class InvalidPaymentMethodException extends RuntimeException {
    public InvalidPaymentMethodException(String message) { super(message); }
}
