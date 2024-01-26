package com.weather.api.service;

import com.weather.api.exception.CustomException;
import org.springframework.stereotype.Service;

import static com.weather.api.exception.ErrorMessage.FAILED;

@Service
public class LocationToCode {
    public String searchBigLocation(String location) {
        if (location.equals("강원도")) {
            return "105";
        } else if (location.equals("전국")) {
            return "108";
        } else if (location.equals("서울") || location.equals("인천") || location.equals("경기도")) {
            return "109";
        } else if (location.equals("충청북도")) {
            return "131";
        } else if (location.equals("대전") || location.equals("세종") || location.equals("충청남도") || location.equals("충남")) {
            return "133";
        } else if (location.equals("전라북도") || location.equals("전북")) {
            return "146";
        } else if (location.equals("광주") || location.equals("전라남도") || location.equals("전남")) {
            return "156";
        } else if (location.equals("대구") || location.equals("경상북도") || location.equals("경북")) {
            return "143";
        } else if (location.equals("부산") || location.equals("울산") || location.equals("경상남도") || location.equals("경남")) {
            return "159";
        } else if (location.equals("제주") || location.equals("제주도")) {
            return "184";
        } else {
            throw new CustomException(FAILED);
        }
    }
}
