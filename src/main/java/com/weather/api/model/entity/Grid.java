package com.weather.api.model.entity;

import lombok.Getter;

@Getter
public class Grid {
    private double x;
    private double y;

    public Grid(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
