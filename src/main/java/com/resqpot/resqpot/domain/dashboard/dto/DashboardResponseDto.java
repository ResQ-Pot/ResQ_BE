// src/main/java/com/resqpot/resqpot/domain/dashboard/dto/DashboardResponseDto.java
package com.resqpot.resqpot.domain.dashboard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DashboardResponseDto {

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("current_weather")
    private CurrentWeatherDto currentWeather;

    @JsonProperty("disaster_situation")
    private DisasterSituationDto disasterSituation;

    @JsonProperty("action_guide")
    private ActionGuideDto actionGuide;

    @JsonProperty("plant_status")
    private PlantStatusDto plantStatus;

    // ── 중첩 DTO ──────────────────────────────────────────────

    @Getter
    @Builder
    public static class CurrentWeatherDto {
        private Double temperature;

        @JsonProperty("precipitation_probability")
        private Integer precipitationProbability;

        @JsonProperty("wind_speed")
        private Double windSpeed;
    }

    @Getter
    @Builder
    public static class DisasterSituationDto {
        @JsonProperty("status_name")
        private String statusName;   // 예: "태풍 주의보" / "이상 없음"

        @JsonProperty("risk_score")
        private Integer riskScore;   // UserProfile.currentRiskScore
    }

    @Getter
    @Builder
    public static class ActionGuideDto {
        private String title;

        @JsonProperty("recommended_action")
        private String recommendedAction;
    }

    @Getter
    @Builder
    public static class PlantStatusDto {
        @JsonProperty("is_connected")
        private boolean isConnected;

        private String expression;       // 예: "SCARED"
        
        @JsonProperty("persona_message")
        private String personaMessage;   // 예: "비바람이 너무 거세서 무서워요!"
    }
}