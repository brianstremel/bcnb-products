package com.brianstremel.bcnbproducts.product.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorData(String path,
                        String message,
                        int statusCode,
                        LocalDateTime timestamp,
                        List<String> errors,
                        Map<String, String> validationErrors) {

    public static ErrorData of(String path, String message, int statusCode) {
        return new ErrorData(path, message, statusCode, LocalDateTime.now(), null, null);
    }

    public static ErrorData ofValidationError(String path, String message, int statusCode, Map<String, String> validationErrors) {
        return new ErrorData(path, message, statusCode, LocalDateTime.now(), null, validationErrors);
    }
}
