package com.brianstremel.bcnbproducts.product.exception;

import com.brianstremel.bcnbproducts.product.exception.custom.ProductPriceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;


import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ProductPriceNotFoundException.class)
    public ResponseEntity<ErrorData> handleResourceNotFoundException(
            ProductPriceNotFoundException ex,
            HttpServletRequest request) {

        final ErrorData errorData = ErrorData.of(
                request.getRequestURI(),
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorData);
    }


    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorData> handleMethodValidationErrors(
            HandlerMethodValidationException ex,
            HttpServletRequest request) {

        final Map<String, String> validationErrors = ex.getAllValidationResults()
                .stream()
                .flatMap(result -> result.getResolvableErrors().stream()
                        .map(error -> Map.entry(
                                getParameterName(error),
                                error.getDefaultMessage() != null ? error.getDefaultMessage() : "Validation error"
                        )))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (error1, error2) -> error1
                ));

        final var apiError = ErrorData.ofValidationError(
                request.getRequestURI(),
                "Validation error",
                HttpStatus.BAD_REQUEST.value(),
                validationErrors
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorData> handleGenericException(
            HttpServletRequest request,
            Exception ex) {

        final ErrorData errorData = ErrorData.of(
                request.getRequestURI(),
                "Internal Server Error",
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorData);
    }

    private String getParameterName(MessageSourceResolvable error) {
        String[] codes = error.getCodes();
        if (codes != null && codes.length > 0) {
            String lastCode = codes[codes.length - 1];
            return lastCode.substring(lastCode.lastIndexOf('.') + 1);
        }
        return "unknown";
    }
}
