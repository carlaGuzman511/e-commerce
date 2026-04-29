package com.kiosko.authservice.exception;

public class PermissionAlreadyExistsException extends RuntimeException {
    public PermissionAlreadyExistsException(String message) {
        super(message);
    }
}