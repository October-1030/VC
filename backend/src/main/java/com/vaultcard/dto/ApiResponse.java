package com.vaultcard.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Generic API response wrapper.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    /**
     * Whether the request was successful
     */
    private boolean success;

    /**
     * Response data (if successful)
     */
    private T data;

    /**
     * Error message (if failed)
     */
    private String error;

    /**
     * Error code (if failed)
     */
    private String errorCode;

    /**
     * Response timestamp
     */
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    /**
     * Create a successful response
     */
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * Create an error response
     */
    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .error(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * Create an error response with code
     */
    public static <T> ApiResponse<T> error(String code, String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .errorCode(code)
                .error(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
