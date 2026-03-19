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

        // 1. 병원 (구글 공식 type: hospital)
        allFacilities.addAll(googleApiClient.searchPlaces(lat, lng, radius, "hospital", "HOSPITAL"));

        // 2. 약국 (구글 공식 type: pharmacy)
        allFacilities.addAll(googleApiClient.searchPlaces(lat, lng, radius, "pharmacy", "PHARMACY"));

        // 3. 대피소 (공식 type이 없으므로 keyword 검색)
        allFacilities.addAll(googleApiClient.searchByKeyword(lat, lng, radius, "대피소", "SHELTER"));

        // 4. 소방서 (구글 공식 type: fire_station)
        allFacilities.addAll(googleApiClient.searchPlaces(lat, lng, radius, "fire_station", "FIRE_STATION"));

        // 5. 경찰서 (구글 공식 type: police)
        allFacilities.addAll(googleApiClient.searchPlaces(lat, lng, radius, "police", "POLICE"));

        // 6. 편의점 (구글 공식 type: convenience_store)
        allFacilities.addAll(googleApiClient.searchPlaces(lat, lng, radius, "convenience_store", "CONVENIENCE"));

        // 7. 자동심장충격기 (AED) (공식 type 없음 -> keyword 검색)
        // 💡 "AED"보다 "자동심장충격기"로 검색하는 게 한국 지도에서는 더 정확하게 나옵니다!
        allFacilities.addAll(googleApiClient.searchByKeyword(lat, lng, radius, "자동심장충격기", "AED"));

        // 8. 식수/급수 (공식 type 없음 -> keyword 검색)
        allFacilities.addAll(googleApiClient.searchByKeyword(lat, lng, radius, "식수", "WATER"));

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