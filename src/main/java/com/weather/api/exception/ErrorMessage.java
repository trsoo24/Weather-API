package com.weather.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    INVALID_ADDRESS(HttpStatus.BAD_REQUEST, 400, "유효하지 않은 도로명 주소입니다."),
    FAILED(HttpStatus.BAD_REQUEST, 400, "요청에 실패했습니다."),
    API_FAILED(HttpStatus.BAD_REQUEST, 400, "API 호출에 실패했습니다."),
    NOT_IN_DATABASE(HttpStatus.BAD_REQUEST, 400, "조회할 수 있는 내용이 없습니다. Open API 로 조회해주세요."),
    NEED_CHANGE_DATE(HttpStatus.BAD_REQUEST, 400, "최근 1일전 날짜만 조회가 가능합니다."),
    API_NO_DATA(HttpStatus.BAD_REQUEST, 400, "사이트에서 조회할 데이터가 없습니다.")
    ;

    private final HttpStatus status;
    private final int code;
    private final String reason;
}
