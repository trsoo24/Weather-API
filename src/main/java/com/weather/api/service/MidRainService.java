package com.weather.api.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.weather.api.exception.CustomException;
import com.weather.api.model.dto.MidLocationCode;
import com.weather.api.model.entity.MidTermRain;
import com.weather.api.repository.MidTermRainRepository;
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
public class MidRainService {
    private final MidTermRainRepository midTermRainRepository;
    private final LocationToCode locationToCode;

    public MidTermRain searchMidRain(MidLocationCode midLocationCode) {
        String requestUrl = "http://apis.data.go.kr/1360000/MidFcstInfoService/getMidLandFcst";
        String regionId = locationToCode.searchRainLocation(midLocationCode.getLocation());
        String date = midLocationCode.getDateTime().substring(0, 9);

        try {
            String apiKey = "1BVcQIZnVo3dVK3Ina%2Bg4rM6T6h3Ykw1rDZNd6nuUV0oZ44UcxZPfnnQ%2FVvmp65159ylHhDWaFTFRmpeGjWmvw%3D%3D";
            String request = "?serviceKey=" + apiKey + "&numOfRows=1000&pageNo=1&regId=" +
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

            br.close();
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

            JsonObject object = resultArray.getAsJsonObject();
            String rnSt3Am = object.getAsJsonObject().get("rnSt3Am").getAsString();
            String rnSt3Pm = object.getAsJsonObject().get("rnSt3Pm").getAsString();
            String rnSt4Am = object.getAsJsonObject().get("rnSt4Am").getAsString();
            String rnSt4Pm = object.getAsJsonObject().get("rnSt4Pm").getAsString();
            String rnSt5Am = object.getAsJsonObject().get("rnSt5Am").getAsString();
            String rnSt5Pm = object.getAsJsonObject().get("rnSt5Pm").getAsString();
            String rnSt6Am = object.getAsJsonObject().get("rnSt6Am").getAsString();
            String rnSt6Pm = object.getAsJsonObject().get("rnSt6Pm").getAsString();
            String rnSt7Am = object.getAsJsonObject().get("rnSt7Am").getAsString();
            String rnSt7Pm = object.getAsJsonObject().get("rnSt7Pm").getAsString();
            String rnSt8 = object.getAsJsonObject().get("rnSt8").getAsString();
            String rnSt9 = object.getAsJsonObject().get("rnSt9").getAsString();
            String rnSt10 = object.getAsJsonObject().get("rnSt10").getAsString();
            String wf3Am = object.getAsJsonObject().get("wf3Am").getAsString();
            String wf3Pm = object.getAsJsonObject().get("wf3Pm").getAsString();
            String wf4Am = object.getAsJsonObject().get("wf4Am").getAsString();
            String wf4Pm = object.getAsJsonObject().get("wf4Pm").getAsString();
            String wf5Am = object.getAsJsonObject().get("wf5Am").getAsString();
            String wf5Pm = object.getAsJsonObject().get("wf5Pm").getAsString();
            String wf6Am = object.getAsJsonObject().get("wf6Am").getAsString();
            String wf6Pm = object.getAsJsonObject().get("wf6Pm").getAsString();
            String wf7Am = object.getAsJsonObject().get("wf7Am").getAsString();
            String wf7Pm = object.getAsJsonObject().get("wf7Pm").getAsString();
            String wf8 = object.getAsJsonObject().get("wf8").getAsString();
            String wf9 = object.getAsJsonObject().get("wf9").getAsString();
            String wf10 = object.getAsJsonObject().get("wf10").getAsString();

            MidTermRain midTermRain = MidTermRain.builder()
                    .date(date)
                    .location(midLocationCode.getLocation())
                    .rnSt3Am(rnSt3Am)
                    .rnSt3Pm(rnSt3Pm)
                    .rnSt4Am(rnSt4Am)
                    .rnSt4Pm(rnSt4Pm)
                    .rnSt5Am(rnSt5Am)
                    .rnSt5Pm(rnSt5Pm)
                    .rnSt6Am(rnSt6Am)
                    .rnSt6Pm(rnSt6Pm)
                    .rnSt7Am(rnSt7Am)
                    .rnSt7Pm(rnSt7Pm)
                    .rn8St(rnSt8)
                    .rn9St(rnSt9)
                    .rn10St(rnSt10)
                    .wf3Am(wf3Am)
                    .wf3Pm(wf3Pm)
                    .wf4Am(wf4Am)
                    .wf4Pm(wf4Pm)
                    .wf5Am(wf5Am)
                    .wf5Pm(wf5Pm)
                    .wf6Am(wf6Am)
                    .wf6Pm(wf6Pm)
                    .wf7Am(wf7Am)
                    .wf7Pm(wf7Pm)
                    .wf8(wf8)
                    .wf9(wf9)
                    .wf10(wf10)
                    .build();
            return midTermRainRepository.save(midTermRain);

        } catch (IOException e) {
            throw new CustomException(FAILED);
        }
    }
}
