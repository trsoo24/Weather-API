package com.weather.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MidLocationCode {
    private String dateTime; // "202401270600"
    private String location; // 기온&강수량 과 전망 코드에서 필요로 하는 값이 다름 LocationToCode 참조
}
