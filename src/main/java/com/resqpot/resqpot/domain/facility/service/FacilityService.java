// src/main/java/com/resqpot/resqpot/domain/facility/service/FacilityService.java
package com.resqpot.resqpot.domain.facility.service;

import com.resqpot.resqpot.domain.facility.client.GooglePlacesApiClient;
import com.resqpot.resqpot.domain.facility.dto.FacilityDto;
import com.resqpot.resqpot.domain.facility.dto.FacilityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FacilityService {

    // 구글 API랑 통신하는 심부름꾼 (WebClient 등으로 구현 예정)
    private final GooglePlacesApiClient googleApiClient;

    public FacilityResponse getAllNearbyFacilities(double lat, double lng, int radius) {
        List<FacilityDto> allFacilities = new ArrayList<>();

        // 1. 구글에 "병원(hospital)" 내놔! 하고 요청
        List<FacilityDto> hospitals = googleApiClient.searchPlaces(lat, lng, radius, "hospital", "HOSPITAL");
        allFacilities.addAll(hospitals);

        // 2. 구글에 "약국(pharmacy)" 내놔! 하고 요청
        List<FacilityDto> pharmacies = googleApiClient.searchPlaces(lat, lng, radius, "pharmacy", "PHARMACY");
        allFacilities.addAll(pharmacies);

        // 3. 구글에 "대피소(keyword)" 내놔! 하고 요청
        List<FacilityDto> shelters = googleApiClient.searchByKeyword(lat, lng, radius, "대피소", "SHELTER");
        allFacilities.addAll(shelters);

        // 4. 거리가 가까운 순서대로 전체 리스트 예쁘게 정렬 (오름차순)
        allFacilities.sort(Comparator.comparing(FacilityDto::getDistanceMeters));

        // 5. 프론트엔드에 응답
        return FacilityResponse.builder()
                .centerLocation(new FacilityResponse.CenterLocation(lat, lng)) // 객체로 예쁘게 감싸기
                .totalCount(allFacilities.size())
                .facilities(allFacilities)
                .build();
    }
}