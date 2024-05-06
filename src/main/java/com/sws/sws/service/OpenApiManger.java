package com.sws.sws.service;

import com.sws.sws.dto.library.LocationListResponse;
import com.sws.sws.dto.library.OpenApiDto;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OpenApiManger {

    private final String BASE_URL = "https://openapi.gg.go.kr/TBGGIBLLBR";
    private final String KEY = "?KEY=d3664ab6de52470fbb0d5e52e8ad7e8b";
    private final String TYPE = "&Type=json";
    private final String pIndex = "&pIndex=1";
    private final String pSize = "&pSize=10";

    private String makeBaseUrl() {
        return BASE_URL + KEY + TYPE + pIndex + pSize;
    }

    public List<OpenApiDto> fetch(String url) {
        List<OpenApiDto> result = new ArrayList<>();
        try {
            RestTemplate restTemplate = new RestTemplate();
            String jsonString = restTemplate.getForObject(url, String.class);
            JSONParser jsonParser = new JSONParser();

            JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);
            JSONObject jsonResponse = (JSONObject) jsonObject.get("response");
            JSONObject jsonBody = (JSONObject) jsonResponse.get("body");
            JSONObject jsonItems = (JSONObject) jsonBody.get("items");
            JSONArray jsonItemList = (JSONArray) jsonItems.get("item");

            for (Object o : jsonItemList) {
                JSONObject item = (JSONObject) o;
                OpenApiDto dto = makeLocationDto(item);
                if (dto == null) continue;
                result.add(dto);
            }
            return result;
        } catch (Exception e) {
            throw new Exception("오픈 API 예외 = fetch 로 가져온 데이터가 비어있음 (데이터 요청 방식 오류)");
        } finally {
            return result;
        }
    }

    private OpenApiDto makeLocationDto(JSONObject item) {
        // 가끔 좌표 데이터가 타입이 다른경우 처리
        if (item.get("mapX") instanceof String || item.get("mapY") instanceof String
                || item.get("addr") == null || item.get("libraryName") == null
                || item.get("openTime") == null || item.get("TelNum") == null) {
            return null;
        }
        return OpenApiDto.builder().
                addr((String) item.get("addr")).
                libraryName((String) item.get("libraryName")).
                openTime((String) item.get("openTime")).
                TelNum((String) item.get("TelNum")).
                mapX((double) item.get("mapX")).
                mapY((double) item.get("mapY")).
                build();
    }
}
