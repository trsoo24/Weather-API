package com.weather.api.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomErrorResponse {
    private HttpStatus status;
    private int code;
    private String reason;

    public static ResponseEntity<CustomErrorResponse> toResponseEntity(ErrorMessage e){
        return ResponseEntity.status(e.getStatus())
                .body(CustomErrorResponse.builder()
                                .status(e.getStatus())
                                .code(e.getCode())
                                .reason(e.getReason())
                                .build());
    }
}
