package com.kiosco.order_service.exception;

public class InvalidTenantException extends RuntimeException {
    public InvalidTenantException(String message) { super(message); }
}
