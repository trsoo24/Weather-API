package com.weather.api.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.weather.api.exception.CustomException;
import com.weather.api.model.dto.DateInfo;
import com.weather.api.model.dto.Grid;
import com.weather.api.model.dto.Point;
import com.weather.api.model.entity.UltShortTerm;
import com.weather.api.repository.UltShortTermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.weather.api.exception.ErrorMessage.FAILED;
import static com.weather.api.exception.ErrorMessage.INVALID_ADDRESS;

@Service
@RequiredArgsConstructor
public class UltShortTermService {
    private final AddressToPoint addressToPoint;
    private final TransCoordinate transCoordinate;
    private final UltShortTermRepository ultShortTermRepository;
    HashMap<String, HashMap<String, String>> hm = new HashMap<>(); // <날짜&시간 , 초단기예보>

    public List<UltShortTerm> searchUltShortTerm(DateInfo dateInfo) {
        String requestUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst";

        Point point = addressToPoint.getMapString(dateInfo.getAddress());
        Grid grid = transCoordinate.toGrid(point);

        // TODO : 이미 조회했던 날짜 값은 repository 안에서 찾기

        try {
            String apiKey = "1BVcQIZnVo3dVK3Ina%2Bg4rM6T6h3Ykw1rDZNd6nuUV0oZ44UcxZPfnnQ%2FVvmp65159ylHhDWaFTFRmpeGjWmvw%3D%3D";
            String request = "?serviceKey=" + apiKey + "&pageNo=1&numOfRows=1000&dataType=JSON&base_date=" +
                    dateInfo.getDate() + "&base_time=" + dateInfo.getTime() + "&nx=" + grid.getX() + "&ny=" + grid.getY();

            URL url = new URL(requestUrl + request);
            HttpURLConnection conUrl = (HttpURLConnection) url.openConnection();
            conUrl.setRequestMethod("GET");
            conUrl.setRequestProperty("Content-type", "application/json");
            BufferedReader br = new BufferedReader(new InputStreamReader(conUrl.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            br.close();
            conUrl.disconnect();

            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(result).getAsJsonObject();
            JsonObject response = (JsonObject) jsonObject.get("response");
            JsonObject responseHeader = (JsonObject) response.get("header");
            String resultCode = responseHeader.get("resultCode").getAsString();

            if (!resultCode.equals("00")) {
                throw new CustomException(FAILED);
            }

            JsonObject responseBody = (JsonObject) response.get("body");
            JsonObject responseItems = (JsonObject) responseBody.get("items");
            JsonArray resultArray = responseItems.getAsJsonArray("item");

            String category = "";
            String fcstDate = "";
            String fcstTime = "";
            String value = "";

            for(JsonElement element : resultArray) {
                JsonObject object = element.getAsJsonObject();
                category = object.getAsJsonObject().get("category").getAsString();
                fcstDate = object.getAsJsonObject().get("fcstDate").getAsString();
                fcstTime = object.getAsJsonObject().get("fcstTime").getAsString();
                value = object.getAsJsonObject().get("fcstValue").getAsString();
                String date = fcstDate.substring(0, 4) + "년"
                        + fcstDate.substring(4, 6) + "월"
                        + fcstDate.substring(6) + "일 "
                        + fcstTime.substring(0, 2) + "시";

                putInfo(date, category, value);
            }

            List<UltShortTerm> list = new ArrayList<>();
            for(String mapKey : hm.keySet()) {
                list.add(makeEntity(dateInfo.getAddress(), mapKey));
            }

            return list;
        } catch (IOException e) {
            throw new CustomException(INVALID_ADDRESS);
        }
    }

    private void putInfo(String date, String category, String value) {
        if(!hm.containsKey(date)) {
            HashMap<String, String> columnHm = new HashMap<>();
            columnHm.put(category, value);
            hm.put(date, columnHm);
        } else {
            HashMap<String, String> columnHm = hm.get(date);
            columnHm.put(category, value);
        }
    }

    @Transactional
    private UltShortTerm makeEntity(String address, String date) {
        UltShortTerm ultShortTerm = new UltShortTerm();
        ultShortTerm.setDateTime(date);
        HashMap<String, String> columnHm = hm.get(date);
        ultShortTerm.setAddress(address);

        for (Map.Entry<String, String> entry : columnHm.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            switch (key) {
                case "T1H" :
                    ultShortTerm.setT1h(value);
                    break;
                case "RN1" :
                    ultShortTerm.setRn1(value);
                    break;
                case "SKY" :
                    ultShortTerm.setSky(value);
                    break;
                case "UUU" :
                    ultShortTerm.setUuu(value);
                    break;
                case "VVV" :
                    ultShortTerm.setVvv(value);
                    break;
                case "REH" :
                    ultShortTerm.setReh(value);
                    break;
                case "PTY" :
                    ultShortTerm.setPty(value);
                    break;
                case "LGT" :
                    ultShortTerm.setLgt(value);
                    break;
                case "VEC" :
                    ultShortTerm.setVec(value);
                    break;
                case "WSD" :
                    ultShortTerm.setWsd(value);
                    break;
            }
        }
        return ultShortTermRepository.save(ultShortTerm);
    }
}
