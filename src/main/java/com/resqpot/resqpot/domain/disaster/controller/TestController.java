// src/main/java/com/resqpot/resqpot/domain/disaster/controller/TestController.java
package com.resqpot.resqpot.domain.disaster.controller;

import com.resqpot.resqpot.domain.disaster.dto.LocationData;
import com.resqpot.resqpot.domain.disaster.dto.WeatherData;
import com.resqpot.resqpot.domain.disaster.service.RiskAnalysisService;
import com.resqpot.resqpot.domain.user.entity.UserProfile;
import com.resqpot.resqpot.domain.user.repository.UserProfileRepository; // 이거 없으시면 만들어야 합니다!
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final RiskAnalysisService riskAnalysisService;
    private final UserProfileRepository userProfileRepository; // JPA 리포지토리

    @GetMapping("/api/test/risk/{profileId}")
    public RiskAnalysisService.RiskResult testRiskCalculation(@PathVariable Long profileId) {

        // 1. DB에서 우리가 방금 넣은 더미 유저 꺼내오기
        UserProfile profile = userProfileRepository.findById(profileId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        // 2. 가상의 극한 재난 상황 세팅 (Mocking)
        WeatherData weather = WeatherData.builder()
                .rainfallPerHour(100.0) // 폭우 100mm (9점)
                .windSpeed(20.0)        // 강풍 20m/s (8점)
                .temperature(25.0)      // 정상 기온 (0점)
                .snowfall(0.0)
                .build();

        LocationData location = LocationData.builder()
                .disasterHistoryScore(8)   // 산사태 이력 있음 (8점)
                .distanceToMountainKm(0.5) // 산 바로 밑 (5점)
                .distanceToSeaKm(50.0)     // 바다랑 멈 (0점)
                .build();

        String alertKeyword = "태풍"; // 재난문자 (10점)

        // 3. 계산기 실행!
        return riskAnalysisService.calculateTotalRisk(profile, weather, location, alertKeyword);
    }
}