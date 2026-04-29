package com.kiosco.order_service.exception;

public class InvalidDeliveryMethodException extends RuntimeException {
    public InvalidDeliveryMethodException(String message) { super(message); }
}
