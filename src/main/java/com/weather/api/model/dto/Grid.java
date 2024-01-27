package com.weather.api.model.dto;

import lombok.Getter;

@Getter
public class Grid {
    private int y;
    private int x;

    public Grid(int y, int x) {
        this.y = y;
        this.x = x;
    }
}
