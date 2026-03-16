// src/main/java/com/resqpot/resqpot/domain/disaster/service/RiskAnalysisService.java
package com.resqpot.resqpot.domain.disaster.service;

import com.resqpot.resqpot.domain.disaster.dto.LocationData;
import com.resqpot.resqpot.domain.disaster.dto.WeatherData;
import com.resqpot.resqpot.domain.user.entity.UserProfile;
import org.springframework.stereotype.Service;

@Service
public class RiskAnalysisService {

    /**
     * 총 위험 점수 및 단계 계산 로직
     */
    public RiskResult calculateTotalRisk(UserProfile profile, WeatherData weather, LocationData location, String currentAlert) {
        int totalScore = 0;

        totalScore += calculateEnvironmentalRisk(location); // 1. 지역/환경 위험도 (최대 20점)
        totalScore += calculateClimateRisk(weather);        // 2. 기후 재난 위험도 (최대 40점)
        totalScore += calculateAlertRisk(currentAlert);     // 3. 재난 문자 위험도 (최대 10점)
        totalScore += calculateUserVulnerability(profile);  // 4. 사용자 취약도 (최대 30점)

        // 최대 점수 100점 제한
        totalScore = Math.min(totalScore, 100);

        // 위험 단계 판단
        String riskLevel = determineRiskLevel(totalScore);

        return new RiskResult(totalScore, riskLevel);
    }

    // 1. 지역/환경 위험도
    private int calculateEnvironmentalRisk(LocationData location) {
        int score = 0;
        score += location.getDisasterHistoryScore();

        // 산 거리 (최대 5)
        if (location.getDistanceToMountainKm() < 1) score += 5;
        else if (location.getDistanceToMountainKm() <= 3) score += 3;

        // 바다 거리 (최대 5)
        if (location.getDistanceToSeaKm() < 3) score += 5;
        else if (location.getDistanceToSeaKm() <= 10) score += 3;

        return score;
    }

    // 2. 기후 재난 위험도
    private int calculateClimateRisk(WeatherData weather) {
        int score = 0;

        // 강수량
        double rain = weather.getRainfallPerHour();
        if (rain >= 150) score += 10;
        else if (rain >= 80) score += 9;
        else if (rain >= 20) score += 7;
        else if (rain >= 5) score += 4;
        else if (rain >= 1) score += 2;
        else if (rain > 0) score += 1;

        // 풍속
        double wind = weather.getWindSpeed();
        if (wind >= 26) score += 10;
        else if (wind >= 19) score += 8;
        else if (wind >= 13) score += 5;
        else if (wind >= 8) score += 3;
        else if (wind >= 3) score += 1;

        // 기온
        double temp = weather.getTemperature();
        if (temp <= -12) score += 10;
        else if (temp <= -5) score += 8;
        else if (temp <= 0) score += 6;
        else if (temp <= 5) score += 3;
        else if (temp >= 35) score += 10;
        else if (temp >= 33) score += 7;
        else if (temp >= 30) score += 4;

        // 적설량
        double snow = weather.getSnowfall();
        if (snow >= 30) score += 10;
        else if (snow >= 10) score += 8;
        else if (snow >= 5) score += 6;
        else if (snow >= 1) score += 3;
        else if (snow >= 0.2) score += 1;

        return score;
    }

    // 3. 재난 문자 위험도
    private int calculateAlertRisk(String alertKeyword) {
        if (alertKeyword == null) return 0;
        return switch (alertKeyword) {
            case "태풍", "산사태" -> 10;
            case "침수" -> 9;
            case "폭염" -> 6;
            case "미세먼지" -> 4;
            default -> 0;
        };
    }

    // 4. 사용자 취약도
    private int calculateUserVulnerability(UserProfile profile) {
        int score = 0;

        // 1) 연령 - null 방지를 위해 Integer로 받고, 비어있으면 일반 성인(30세, 0점)으로 간주
        Integer ageObj = profile.getAge();
        int age = ageObj != null ? ageObj : 30;

        if (age >= 75) score += 10;
        else if (age >= 65) score += 5;
        else if (age <= 12) score += 7;
        else if (age <= 18) score += 3;

        // 2) 주거지 (equals는 앞에 문자열 상수를 두면 Null-Safe함)
        String residence = profile.getResidenceType();
        if ("반지하".equals(residence) || "저지대".equals(residence)) score += 10;
        else if ("고층아파트".equals(residence)) score += 6;
        else if ("옥탑".equals(residence)) score += 3;

        // 3) 동거인 수
        String cohabitants = profile.getCohabitantsInfo();
        if ("1인가구".equals(cohabitants)) score += 5;
        else if ("2명".equals(cohabitants)) score += 2;

        // 4) 장애 여부 - specialNotes가 null일 수 있으므로 반드시 null 체크 후 contains 실행
        String disability = profile.getSpecialNotes();
        if (disability != null) {
            if (disability.contains("거동불편") || disability.contains("판단어려움")) score += 5;
            else if (disability.contains("경증장애")) score += 3;
        }

        return score;
    }

    // 5. 위험 단계 판단
    private String determineRiskLevel(int totalScore) {
        if (totalScore >= 80) return "DANGER";
        if (totalScore >= 40) return "WARNING";
        return "SAFE";
    }

    // 반환용 내부 DTO
    public record RiskResult(int totalScore, String riskLevel) {}
}