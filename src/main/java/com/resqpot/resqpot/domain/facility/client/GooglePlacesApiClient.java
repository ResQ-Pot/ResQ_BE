// src/main/java/com/resqpot/resqpot/domain/facility/client/GooglePlacesApiClient.java
package com.resqpot.resqpot.domain.facility.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.resqpot.resqpot.domain.facility.dto.FacilityDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GooglePlacesApiClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper; // JSON 파서

    @Value("${google.places.api-key}")
    private String apiKey;

    @Value("${google.places.base-url}")
    private String baseUrl;

    /**
     * 타입(hospital, pharmacy 등)으로 주변 시설 검색
     */
    public List<FacilityDto> searchPlaces(double lat, double lng, int radius, String googleType, String myType) {
        String url = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("location", lat + "," + lng)
                .queryParam("radius", radius)
                .queryParam("type", googleType)
                .queryParam("language", "ko")
                .queryParam("key", apiKey)
                .toUriString();

        return fetchAndParse(url, myType);
    }

    /**
     * 키워드("대피소" 등)로 주변 시설 검색
     */
    public List<FacilityDto> searchByKeyword(double lat, double lng, int radius, String keyword, String myType) {
        String url = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("location", lat + "," + lng)
                .queryParam("radius", radius)
                .queryParam("keyword", keyword)
                .queryParam("language", "ko")
                .queryParam("key", apiKey)
                .toUriString();

        return fetchAndParse(url, myType);
    }

    /**
     * 구글 API 호출 및 JSON 파싱 공통 메서드 (에러 안 나는 String 방식)
     */
    private List<FacilityDto> fetchAndParse(String url, String myType) {
        List<FacilityDto> resultList = new ArrayList<>();

        try {
            // 1. 문자열(String)로 안전하게 받아오기
            String responseBody = restTemplate.getForObject(url, String.class);

            if (responseBody != null) {
                // 2. ObjectMapper로 JsonNode 트리 만들기
                JsonNode response = objectMapper.readTree(responseBody);

                // 3. 상태가 "OK"일 때만 파싱
                if (response.has("status") && "OK".equals(response.get("status").asText())) {
                    for (JsonNode place : response.get("results")) {

                        String placeId = place.get("place_id").asText();
                        String name = place.get("name").asText();
                        double placeLat = place.get("geometry").get("location").get("lat").asDouble();
                        double placeLng = place.get("geometry").get("location").get("lng").asDouble();
                        String address = place.has("vicinity") ? place.get("vicinity").asText() : "";

                        String extraInfo = null;
                        if (place.has("opening_hours")) {
                            boolean openNow = place.get("opening_hours").get("open_now").asBoolean();
                            extraInfo = openNow ? "현재 영업중" : "영업 종료";
                        }

                        resultList.add(FacilityDto.builder()
                                .id(placeId)
                                .type(myType)
                                .name(name)
                                .lat(placeLat)
                                .lng(placeLng)
                                .address(address)
                                .distanceMeters(0)
                                .extraInfo(extraInfo)
                                .build());
                    }
                } else {
                    log.warn("구글 API 상태가 OK가 아님: {}", response.has("status") ? response.get("status").asText() : "알 수 없음");
                }
            }
        } catch (Exception e) {
            log.error("구글 Places API 파싱 실패: {}", e.getMessage());
        }

        return resultList;
    }
}