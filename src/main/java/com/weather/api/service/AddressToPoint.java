package com.weather.api.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.weather.api.exception.CustomException;
import com.weather.api.model.entity.Point;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static com.weather.api.exception.ErrorMessage.INVALID_ADDRESS;

@Service
public class AddressToPoint {
    @Value("${spring.address.key}")
    private String apiKey;
    private final String type = "EPSG:4326";

    public Point getMapString(String searchAddr) {
        StringBuilder sb = new StringBuilder("https://api.vworld.kr/req/address");
        sb.append("?service=address");
        sb.append("&request=GetCoord");
        sb.append("&format=json");
        sb.append("&crs=" + type);
        sb.append("&key=" + apiKey);
        sb.append("&type=road"); // 도로명 주소로 고정
        sb.append("&address=" + URLEncoder.encode(searchAddr, StandardCharsets.UTF_8));

        try {
            URL url = new URL(sb.toString());
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));

            JsonParser jspa = new JsonParser();
            JsonObject jsob = jspa.parse(reader).getAsJsonObject();
            JsonObject jsrs = jsob.get("response").getAsJsonObject();
            JsonObject jsResult = jsrs.get("result").getAsJsonObject();
            JsonObject jspoitn = jsResult.get("point").getAsJsonObject();

            String jsonLongitude = jspoitn.get("y").getAsString();
            String jsonLatitude = jspoitn.get("x").getAsString();
            return new Point(Double.parseDouble(jsonLongitude), Double.parseDouble(jsonLatitude));
        } catch (IOException e) {
            throw new CustomException(INVALID_ADDRESS);
        }
    }
}
