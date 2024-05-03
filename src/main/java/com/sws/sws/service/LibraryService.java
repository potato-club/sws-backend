package com.sws.sws.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@Transactional
@Slf4j
public class LibraryService {

    private final String BASE_URL = "https://openapi.gg.go.kr/TBGGIBLLBR";
    private final String KEY = "?KEY=d3664ab6de52470fbb0d5e52e8ad7e8b";
    private final String TYPE = "&Type=xml";
    private final String pIndex = "&pIndex=1";
    private final String pSize = "&pSize=10";

    private String make() {
        return BASE_URL
                + KEY
                + TYPE
                + pIndex
                + pSize;
    }

    public ResponseEntity<?> fetch() {
        System.out.println(make());
        log.info(make());
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
        ResponseEntity<Map> resultMap = restTemplate.exchange(make(), HttpMethod.GET, entity, Map.class);
        System.out.println(resultMap.getBody());
        return resultMap;
    }

}
