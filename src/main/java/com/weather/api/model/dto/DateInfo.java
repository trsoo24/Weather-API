package com.weather.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DateInfo {
    private String date; // "20240127"
    private String time; "0600"
    private String address; "서울 강남구 테헤란로 131"
}
