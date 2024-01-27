package com.weather.api.service;

import com.weather.api.model.entity.Grid;
import com.weather.api.model.entity.Point;
import org.springframework.stereotype.Service;

@Service
public class TransCoordinate {
    /**
     * 1. 주소 값을 넣어서 위, 경도 좌표 값을 받는다.
     * 2. 위, 경도 좌표 값을 기상청 API 에서 필요로 하는 x,y 값으로 변환
     */
    final double RE = 6371; // 지구 반경(km)
    final double GRID = 5.0; // 격자 간격(km)
    final double SLAT1 = 30.0; // 투영 위도1(degree)
    final double SLAT2 = 60.0; // 투영 위도2(degree)
    final double OLON = 126.0; // 기준점 경도(degree)
    final double OLAT = 38.0; // 기준점 위도(degree)
    final double XO = 43; // 기준점 X좌표(GRID)
    final double YO = 136; // 기준점 Y좌표(GRID)

    public Grid toGrid(Point point) {
        double DEGRAD = Math.PI / 180.0;

        double re = RE / GRID;
        double slat1 = SLAT1 * DEGRAD;
        double slat2 = SLAT2 * DEGRAD;
        double olon = OLON * DEGRAD;
        double olat = OLAT * DEGRAD;

        double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
        double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
        double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
        ro = re * sf / Math.pow(ro, sn);

        double latX = point.getLongitude();
        double lonY = point.getLatitude();
        double ra = Math.tan(Math.PI * 0.25 + (latX) * DEGRAD * 0.5);
        ra = re * sf / Math.pow(ra, sn);
        double theta = lonY * DEGRAD - olon;
        if (theta > Math.PI) theta -= 2.0 * Math.PI;
        if (theta < -Math.PI) theta += 2.0 * Math.PI;
        theta *= sn;
        int x = (int) Math.floor(ra * Math.sin(theta) + XO + 0.5);
        int y = (int) Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);

        return new Grid(x, y);
    }
}
