package com.weather.api.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Point {
    private double latitude;
    private double longitude;
}
