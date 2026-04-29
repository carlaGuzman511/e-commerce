package com.kiosco.order_service.exception;

public class InvalidOrderStateException extends RuntimeException {
    public InvalidOrderStateException(String message) { super(message); }
}
