package com.weather.api.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomErrorResponse> customExceptionHandler(CustomException e) {
        return CustomErrorResponse.toResponseEntity(e.getErrorMessage());
    }
}
