package com.resqpot.resqpot.domain.weather.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum WarningType {
    STRONG_WIND("1", "강풍"),
    HEAVY_RAIN("2", "호우"),
    COLD_WAVE("3", "한파"),
    DRY("4", "건조"),
    STORM_SURGE("5", "해일"),
    PUNG_RANG("6", "풍랑"),
    TYPHOON("7", "태풍"),
    SNOW("8", "대설"),
    YELLOW_DUST("9", "황사"),
    HEAT_WAVE("12", "폭염");

    private final String code;
    private final String description;

    public static String getDescription(String code) {
        return Arrays.stream(WarningType.values())
                .filter(t -> t.code.equals(code))
                .findFirst()
                .map(WarningType::getDescription)
                .orElse("기타");
    }
}