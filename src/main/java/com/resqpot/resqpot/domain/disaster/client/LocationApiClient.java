// src/main/java/com/resqpot/resqpot/domain/disaster/client/LocationApiClient.java
package com.resqpot.resqpot.domain.disaster.client;

import com.resqpot.resqpot.domain.disaster.dto.LocationData;
import org.springframework.stereotype.Component;

@Component
public class LocationApiClient {

    public LocationData fetchLocationRisk(String zone, double lat, double lng) {
        // [데모데이 전용 Mock 데이터]
        // 시나리오: "사용자가 안동시의 산 근처에 살고 있다"고 가정합니다.
        return LocationData.builder()
                .disasterHistoryScore(8)      // 과거 산사태 이력 있음 (8점)
                .distanceToMountainKm(0.8)    // 산과 1km 이내 (5점)
                .distanceToSeaKm(50.0)        // 바다와는 멈 (0점)
                .build();
    }
}