package com.weather.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    INVALID_ADDRESS(HttpStatus.BAD_REQUEST, 400, "유효하지 않은 도로명 주소입니다."),
    FAILED(HttpStatus.BAD_REQUEST, 400, "요청에 실패했습니다.")
    ;

    private final HttpStatus status;
    private final int code;
    private final String reason;
}
