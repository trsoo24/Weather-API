package com.weather.api.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.weather.api.exception.CustomException;
import com.weather.api.model.dto.DateInfo;
import com.weather.api.model.dto.Grid;
import com.weather.api.model.dto.Point;
import com.weather.api.model.entity.ShortTerm;
import com.weather.api.repository.ShortTermRepository;
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

import static com.weather.api.exception.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class ShortTermService {
    private final ShortTermRepository shortTermRepository;
    private final AddressToPoint addressToPoint;
    private final TransCoordinate transCoordinate;

    HashMap<String, HashMap<String, String>> hm = new HashMap<>(); // <날짜&시간 , 단기예보>

    public List<ShortTerm> searchShortTerm(DateInfo dateInfo) {
        String requestUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";

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

            conUrl.disconnect();

            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(result).getAsJsonObject();
            JsonObject response = (JsonObject) jsonObject.get("response");
            JsonObject responseHeader = (JsonObject) response.get("header");
            String resultCode = responseHeader.get("resultCode").getAsString();

            if (!resultCode.equals("00")) {
                throw new CustomException(API_FAILED);
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

            List<ShortTerm> list = new ArrayList<>();
            for(String mapKey : hm.keySet()) {
                list.add(makeEntity(dateInfo.getAddress(), mapKey));
            }

            return list;

        } catch (IOException e) {
            throw new CustomException(FAILED);
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
    private ShortTerm makeEntity(String address, String date) {
        ShortTerm shortTerm = new ShortTerm();
        shortTerm.setDateTime(date);
        shortTerm.setAddress(address);
        HashMap<String, String> columnHm = hm.get(date);

        for (Map.Entry<String, String> entry : columnHm.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            switch (key) {
                case "POP" :
                    shortTerm.setPop(value);
                    break;
                case "PTY" :
                    shortTerm.setPty(value);
                    break;
                case "PCP" :
                    shortTerm.setPcp(value);
                    break;
                case "REH" :
                    shortTerm.setReh(value);
                    break;
                case "SNO" :
                    shortTerm.setSno(value);
                    break;
                case "SKY" :
                    shortTerm.setSky(value);
                    break;
                case "TMP" :
                    shortTerm.setTmp(value);
                    break;
                case "TMN" :
                    shortTerm.setTmn(value);
                    break;
                case "TMX" :
                    shortTerm.setTmx(value);
                    break;
                case "UUU" :
                    shortTerm.setUuu(value);
                    break;
                case "VVV" :
                    shortTerm.setVvv(value);
                    break;
                case "WAV" :
                    shortTerm.setWav(value);
                    break;
                case "VEC" :
                    shortTerm.setVec(value);
                    break;
                case "WSD" :
                    shortTerm.setWsd(value);
                    break;
            }
        }
        return shortTermRepository.save(shortTerm);
    }

    public ShortTerm searchInDB(String dateNumber) { // "202401270600" 형식 입력
        String dateTime = dateNumber.substring(0, 4) + "년"
                + dateNumber.substring(4, 6) + "월"
                + dateNumber.substring(6, 8) + "일 "
                + dateNumber.substring(8, 10) + "시";

        return shortTermRepository.findByDateTime(dateTime)
                .orElseThrow(() -> new CustomException(NOT_IN_DATABASE));
    }
}
