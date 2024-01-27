package com.weather.api.service;

import com.weather.api.exception.CustomException;
import org.springframework.stereotype.Service;

import static com.weather.api.exception.ErrorMessage.INVALID_ADDRESS;

@Service
public class LocationToCode {
    public String searchTotalLocation(String location) { // 전망 조회
        if (location.equals("강원도")) {
            return "105";
        } else if (location.equals("전국")) {
            return "108";
        } else if (location.equals("서울") || location.equals("인천") || location.equals("경기도")) {
            return "109";
        } else if (location.equals("충청북도") || location.equals("충북")) {
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
            throw new CustomException(INVALID_ADDRESS);
        }
    }

    public String searchRainLocation(String location) { // 강수량 조회
        if (location.equals("서울") || location.equals("인천") || location.equals("경기도") || location.equals("경기")) {
            return "11B00000";
        } else if (location.equals("강원도영서") || location.equals("강원영서")) {
            return "11D10000";
        } else if (location.equals("강원도영동") || location.equals("강원영동")) {
            return "11D20000";
        } else if (location.equals("대전") || location.equals("세종") || location.equals("충청남도") || location.equals("충남")) {
            return "11C20000";
        } else if (location.equals("충청북도") || location.equals("충북")) {
            return "11C10000";
        } else if (location.equals("광주") || location.equals("전라남도") || location.equals("전남")) {
            return "11F20000";
        } else if (location.equals("전라북도") || location.equals("전북")) {
            return "11F10000";
        } else if (location.equals("대구") || location.equals("경상북도") || location.equals("경북")) {
            return "11H10000";
        } else if (location.equals("부산") || location.equals("울산") || location.equals("경상남도") || location.equals("경남")) {
            return "11H20000";
        } else if (location.equals("제주") || location.equals("제주도")) {
            return "11G00000";
        } else {
            throw new CustomException(INVALID_ADDRESS);
        }
    }

    public String searchTempLocation(String location) { // 기온 조회
        if (location.equals("서울")) {
            return "11B10101";
        } else if (location.equals("인천")) {
            return "11B20201";
        } else if (location.equals("수원")) {
            return "11B20601";
        } else if (location.equals("파주")) {
            return "11B20305";
        } else if (location.equals("춘천")) {
            return "11D10301";
        } else if (location.equals("원주")) {
            return "11D10401";
        } else if (location.equals("강릉")) {
            return "11D20501";
        } else if (location.equals("대전")) {
            return "11C20401";
        } else if (location.equals("서산")) {
            return "11C20101";
        } else if (location.equals("세종")) {
            return "11C20404";
        } else if (location.equals("청주")) {
            return "11C10301";
        } else if (location.equals("제주")) {
            return "11G00201";
        } else if (location.equals("서귀포")) {
            return "11G00401";
        } else if (location.equals("광주")) {
            return "11F20501";
        } else if (location.equals("목포")) {
            return "21F20801";
        } else if (location.equals("여수")) {
            return "11F20401";
        } else if (location.equals("전주")) {
            return "11F10201";
        } else if (location.equals("군산")) {
            return "21F10501";
        } else if (location.equals("부산")) {
            return "11H20201";
        } else if (location.equals("울산")) {
            return "11H20101";
        } else if (location.equals("창원")) {
            return "11H20301";
        } else if (location.equals("대구")) {
            return "11H10701";
        } else if (location.equals("안동")) {
            return "11H10501";
        } else if (location.equals("포항")) {
            return "11H10201";
        } else {
            throw new CustomException(INVALID_ADDRESS);
        }
    }
}
