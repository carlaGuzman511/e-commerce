package com.kiosko.media_service.exeption;

import com.kiosko.media_service.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Object>> handleBusiness(BusinessException ex) {
        ApiResponse<Object> response = ApiResponse.<Object>builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Business error")
                .errors(ex.getMessage())
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFound(NotFoundException ex) {
        ApiResponse<Object> response = ApiResponse.<Object>builder()
                .timestamp(Instant.now())
                .status(HttpStatus.NOT_FOUND.value())
                .message("Not found")
                .errors(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(CommandException.class)
    public ResponseEntity<ApiResponse<Object>> handleCommand(CommandException ex) {
        ApiResponse<Object> response = ApiResponse.<Object>builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Command execution failed")
                .errors(ex.getMessage())
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneric(Exception ex) {
        System.err.println("[ERROR] " + ex.getClass().getSimpleName() + ": " + ex.getMessage());

        ApiResponse<Object> response = ApiResponse.<Object>builder()
                .timestamp(Instant.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Internal error")
                .errors("Unexpected server error")
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
