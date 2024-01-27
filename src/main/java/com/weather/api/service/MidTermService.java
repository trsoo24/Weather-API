package com.weather.api.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.weather.api.exception.CustomException;
import com.weather.api.model.dto.MidLocationCode;
import com.weather.api.model.entity.MidTermTotal;
import com.weather.api.repository.MidTermTotalRepository;
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
public class MidTermService {
    private final MidTermTotalRepository midTermTotalRepository;
    private final LocationToCode locationToCode;

    public MidTermTotal searchTotal(MidLocationCode midLocationCode) {
        String requestUrl = "http://apis.data.go.kr/1360000/MidFcstInfoService/getMidFcst";
        String regionId = locationToCode.searchTotalLocation(midLocationCode.getLocation());
        String dateTime = midLocationCode.getDateTime();

        try {
            String apiKey = "1BVcQIZnVo3dVK3Ina%2Bg4rM6T6h3Ykw1rDZNd6nuUV0oZ44UcxZPfnnQ%2FVvmp65159ylHhDWaFTFRmpeGjWmvw%3D%3D";
            String request = "?serviceKey=" + apiKey + "&pageNo=1&numOfRows=1000&dataType=JSON&stnId=" + regionId +
                    "&tmFc=" + dateTime;

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

            if (resultCode.equals("03")) {
                throw new CustomException(API_NO_DATA);
            } else if (!resultCode.equals("00")) {
                throw new CustomException(API_FAILED);
            }

            JsonObject responseBody = (JsonObject) response.get("body");
            JsonObject responseItems = (JsonObject) responseBody.get("items");
            JsonArray resultArray = responseItems.getAsJsonArray("item");

            String description = "";
            String date = dateTime.substring(0, 5) + "년" +
                    dateTime.substring(5, 7) + "월" +
                    dateTime.substring(7, 9) + "일";

            JsonObject object = resultArray.get(0).getAsJsonObject();
            description = object.getAsJsonObject().get("wfSv").getAsString();

            MidTermTotal midTermTotal = MidTermTotal.builder()
                    .date(date)
                    .location(midLocationCode.getLocation())
                    .description(description)
                    .build();

            midTermTotalRepository.save(midTermTotal);

            return midTermTotal;

        } catch (IOException e) {
            throw new CustomException(FAILED);
        }
    }

    public MidTermTotal findInDB(MidLocationCode midLocationCode) {
        String date = midLocationCode.getDateTime().substring(0, 4) + "년" +
                midLocationCode.getDateTime().substring(4, 6) + "월" +
                midLocationCode.getDateTime().substring(6, 8) + "일";

        return midTermTotalRepository.findByDateAndLocation(date, midLocationCode.getLocation())
                .orElseThrow(() -> new CustomException(NOT_IN_DATABASE));
    }
}
