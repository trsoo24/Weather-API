package com.weather.api.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private ErrorMessage errorMessage;
    private String reason;

    public CustomException(ErrorMessage errorMessage){
        this.errorMessage = errorMessage;
        this.reason = errorMessage.getReason();
    }
}
