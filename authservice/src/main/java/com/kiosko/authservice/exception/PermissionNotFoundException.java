package com.kiosko.authservice.exception;

public class PermissionNotFoundException extends RuntimeException {
    public PermissionNotFoundException(String message) {
        super(message);
    }
}