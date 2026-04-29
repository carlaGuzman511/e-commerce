package com.kiosko.authservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private Instant timestamp;
    private Integer status;
    private String message;
    private T data;
    private Object errors;

    public static <T> ApiResponse<T> success(String message, T data, Integer status) {
        return ApiResponse.<T>builder()
                .timestamp(Instant.now())
                .status(status)
                .message(message)
                .data(data)
                .errors(null)
                .build();
    }

    public static <T> ApiResponse<T> success(T data, Integer status) {
        return ApiResponse.<T>builder()
                .timestamp(Instant.now())
                .status(status)
                .message("Operation successful")
                .data(data)
                .errors(null)
                .build();
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return success(message, data, 200);
    }

    public static <T> ApiResponse<T> success(T data) {
        return success("Operation successful", data, 200);
    }

    public static <T> ApiResponse<T> error(String message, Object errors, Integer status) {
        return ApiResponse.<T>builder()
                .timestamp(Instant.now())
                .status(status)
                .message(message)
                .data(null)
                .errors(errors)
                .build();
    }

    public static <T> ApiResponse<T> error(String message, Integer status) {
        return error(message, null, status);
    }

    public static <T> ApiResponse<T> error(String message) {
        return error(message, null, 500);
    }
}