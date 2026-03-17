package com.resqpot.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DashboardResponse {

    private String userName;
    private CurrentWeather currentWeather;
    private DisasterSituation disasterSituation;
    private ActionGuideDto actionGuide;
    private PlantStatus plantStatus;

    @Getter @Builder
    public static class CurrentWeather {
        private Double temperature;
        private Integer precipitationProbability;
        private Double windSpeed;
    }

    @Getter @Builder
    public static class DisasterSituation {
        private String statusName;
        private Integer riskScore;
    }

    @Getter @Builder
    public static class ActionGuideDto {
        private String title;
        private String recommendedAction;
    }

    @Getter @Builder
    public static class PlantStatus {
        private Boolean isConnected;
        private String expression;
        private String personaMessage;
    }
}