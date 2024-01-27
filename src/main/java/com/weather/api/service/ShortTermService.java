package com.weather.api.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.weather.api.exception.CustomException;
import com.weather.api.model.dto.DateInfo;
import com.weather.api.model.entity.Grid;
import com.weather.api.model.entity.Point;
import com.weather.api.model.entity.ShortTerm;
import com.weather.api.repository.ShortTermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
public class ShortTermService {
    @Value("${spring.secret.key}")
    private String apiKey;
    private final ShortTermRepository shortTermRepository;
    private final AddressToPoint addressToPoint;
    private final TransCoordinate transCoordinate;

    HashMap<String, HashMap<String, String>> hm = new HashMap<>(); // <날짜&시간 , 단기예보>

    public List<ShortTerm> searchShortTerm(DateInfo dateInfo) {
        String requestUrl = "http://apis.data.go.kr/1360000/MidFcstInfoService/getMidFcst";

        Point point = addressToPoint.getMapString(dateInfo.getAddress());
        Grid grid = transCoordinate.toGrid(point);

        // TODO : 이미 조회했던 날짜 값은 repository 안에서 찾기

        try {
            String request = "serviceKey=" + apiKey + "&pageNo=1&numOfRows=1000&dataType=JSON&base_date=" +
                    dateInfo.getDate() + "&base_time=0500&nx=" + grid.getX() + "&ny=" + grid.getY();

            URL url = new URL(requestUrl + request);
            HttpURLConnection conUrl = (HttpURLConnection) url.openConnection();

            conUrl.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(conUrl.getInputStream()));
            StringBuilder sb = new StringBuilder();

            while (br.readLine() != null) {
                sb.append(br.readLine());
            }
            String result = sb.toString();

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
            JsonArray resultArray = (JsonArray) responseItems.get("item");

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

            List<ShortTerm> list = new ArrayList<>();
            for(String mapKey : hm.keySet()) {
                list.add(makeEntity(mapKey));
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
    private ShortTerm makeEntity(String date) {
        ShortTerm shortTerm = new ShortTerm();
        shortTerm.setDateTime(date);
        HashMap<String, String> columnHm = hm.get(date);

        for (Map.Entry<String, String> entry : columnHm.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            switch (key) {
                case "POP" : shortTerm.setPop(value);
                case "PTY" : shortTerm.setPty(value);
                case "PCP" : shortTerm.setPcp(value);
                case "REH" : shortTerm.setReh(value);
                case "SNO" : shortTerm.setSno(value);
                case "SKY" : shortTerm.setSky(value);
                case "TMP" : shortTerm.setTmp(value);
                case "TMN" : shortTerm.setTmn(value);
                case "TMX" : shortTerm.setTmx(value);
                case "UUU" : shortTerm.setUuu(value);
                case "VVV" : shortTerm.setVvv(value);
                case "WAV" : shortTerm.setWav(value);
                case "VEC" : shortTerm.setVec(value);
                case "WSD" : shortTerm.setWsd(value);
            }
        }
        return shortTermRepository.save(shortTerm);
    }
}
