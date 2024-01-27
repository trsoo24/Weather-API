package com.weather.api.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.weather.api.exception.CustomException;
import com.weather.api.model.dto.MidLocationCode;
import com.weather.api.model.entity.MidTermTemp;
import com.weather.api.repository.MidTermTempRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.weather.api.exception.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class MidTempService {
    private final MidTermTempRepository midTermTempRepository;
    private final LocationToCode locationToCode;

    public MidTermTemp searchTemp(MidLocationCode midLocationCode) {
        String requestUrl = "http://apis.data.go.kr/1360000/MidFcstInfoService/getMidTa";
        String regionId = locationToCode.searchTempLocation(midLocationCode.getLocation());
        String date = midLocationCode.getDateTime().substring(0, 4) + "년"
                + midLocationCode.getDateTime().substring(4, 6) + "월"
                + midLocationCode.getDateTime().substring(6, 8) + "일 "
                + midLocationCode.getDateTime().substring(8, 10) + "시";;

        try {
            String apiKey = "1BVcQIZnVo3dVK3Ina%2Bg4rM6T6h3Ykw1rDZNd6nuUV0oZ44UcxZPfnnQ%2FVvmp65159ylHhDWaFTFRmpeGjWmvw%3D%3D";
            String request = "?serviceKey=" + apiKey + "&numOfRows=1000&dataType=JSON&pageNo=1&regId=" +
                    regionId + "&tmFc=" + midLocationCode.getDateTime();

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
                throw new CustomException(FAILED);
            }

            JsonObject responseBody = (JsonObject) response.get("body");
            JsonObject responseItems = (JsonObject) responseBody.get("items");
            JsonArray resultArray = responseItems.getAsJsonArray("item");

            JsonObject object = resultArray.get(0).getAsJsonObject();

            String taMin3 = object.getAsJsonObject().get("taMin3").getAsString();
            String taMax3 = object.getAsJsonObject().get("taMax3").getAsString();
            String taMin4 = object.getAsJsonObject().get("taMin4").getAsString();
            String taMax4 = object.getAsJsonObject().get("taMax4").getAsString();
            String taMin5 = object.getAsJsonObject().get("taMin5").getAsString();
            String taMax5 = object.getAsJsonObject().get("taMax5").getAsString();
            String taMin6 = object.getAsJsonObject().get("taMin6").getAsString();
            String taMax6 = object.getAsJsonObject().get("taMax6").getAsString();
            String taMin7 = object.getAsJsonObject().get("taMin7").getAsString();
            String taMax7 = object.getAsJsonObject().get("taMax7").getAsString();
            String taMin8 = object.getAsJsonObject().get("taMin8").getAsString();
            String taMax8 = object.getAsJsonObject().get("taMax8").getAsString();
            String taMin9 = object.getAsJsonObject().get("taMin9").getAsString();
            String taMax9 = object.getAsJsonObject().get("taMax9").getAsString();
            String taMin10 = object.getAsJsonObject().get("taMin10").getAsString();
            String taMax10 = object.getAsJsonObject().get("taMax10").getAsString();

            MidTermTemp midTermTemp = MidTermTemp.builder()
                    .date(date)
                    .location(midLocationCode.getLocation())
                    .taMin3(taMin3)
                    .taMax3(taMax3)
                    .taMin4(taMin4)
                    .taMax4(taMax4)
                    .taMin5(taMin5)
                    .taMax5(taMax5)
                    .taMin6(taMin6)
                    .taMax6(taMax6)
                    .taMin7(taMin7)
                    .taMax7(taMax7)
                    .taMin8(taMin8)
                    .taMax8(taMax8)
                    .taMin9(taMin9)
                    .taMax9(taMax9)
                    .taMin10(taMin10)
                    .taMax10(taMax10)
                    .build();

            return midTermTempRepository.save(midTermTemp);
        } catch (IOException e) {
            throw new CustomException(FAILED);
        }
    }

    public MidTermTemp findInDB(MidLocationCode midLocationCode) {
        String date = midLocationCode.getDateTime().substring(0, 4) + "년"
                + midLocationCode.getDateTime().substring(4, 6) + "월"
                + midLocationCode.getDateTime().substring(6, 8) + "일";

        return midTermTempRepository.findByDateAndLocation(date, midLocationCode.getLocation())
                .orElseThrow(() -> new CustomException(NOT_IN_DATABASE));
    }
}
