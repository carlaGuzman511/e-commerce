package com.kiosco.order_service.exception;

import com.kiosco.order_service.constants.Messages;
import com.kiosco.order_service.dto.response.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<Map<String, String>> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> Map.of(
                        "field", err.getField(),
                        "message", err.getDefaultMessage() == null ? "invalid" : err.getDefaultMessage()))
                .collect(Collectors.toList());

        ApiResponse<Object> response = ApiResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .message(Messages.VALIDATION_FAILED)
                .errors(errors)
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        List<Map<String, String>> errors = ex.getConstraintViolations()
                .stream()
                .map(cv -> Map.of(
                        "property", cv.getPropertyPath().toString(),
                        "message", cv.getMessage()))
                .collect(Collectors.toList());

        ApiResponse<Object> response = ApiResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .message(Messages.CONSTRAINT_VIOLATION)
                .errors(errors)
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<Object> handleExternalServiceException(ExternalServiceException ex) {
        ApiResponse<Object> response = ApiResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_GATEWAY.value())
                .message(Messages.EXTERNAL_SERVICE_ERROR)
                .errors(ex.getMessage())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> onResourceNotFound(ResourceNotFoundException ex) {
        ApiResponse<Object> response = ApiResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.NOT_FOUND.value())
                .message(Messages.RESOURCE_NOT_FOUND)
                .errors(ex.getMessage())
                .build();

        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(InvalidOrderStateException.class)
    public ResponseEntity<ApiResponse<Object>> onInvalidOrderState(InvalidOrderStateException ex) {
        ApiResponse<Object> response = ApiResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .message(Messages.BAD_REQUEST)
                .errors(ex.getMessage())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(InvalidPaymentMethodException.class)
    public ResponseEntity<ApiResponse<Object>> onInvalidPaymentMethod(InvalidPaymentMethodException ex) {
        ApiResponse<Object> response = ApiResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .message(Messages.BAD_REQUEST)
                .errors(ex.getMessage())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(InvalidDeliveryMethodException.class)
    public ResponseEntity<ApiResponse<Object>> onInvalidDeliveryMethod(InvalidDeliveryMethodException ex) {
        ApiResponse<Object> response = ApiResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .message(Messages.BAD_REQUEST)
                .errors(ex.getMessage())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ApiResponse<Object>> onInsufficientStock(InsufficientStockException ex) {
        ApiResponse<Object> response = ApiResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .message(Messages.BAD_REQUEST)
                .errors(ex.getMessage())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> onGeneric(Exception ex) {
        ex.printStackTrace();

        ApiResponse<Object> response = ApiResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(Messages.INTERNAL_SERVER_ERROR)
                .errors(ex.getMessage())
                .build();

        return ResponseEntity.status(500).body(response);
    }
}
