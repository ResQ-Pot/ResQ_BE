// src/main/java/com/resqpot/resqpot/domain/disaster/dto/WeatherData.java
package com.resqpot.resqpot.domain.disaster.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 기상청 API에서 받아온 실시간 날씨를 잠깐 담아두는 DTO (DB 저장 X)
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherData {

    private double rainfallPerHour; // 시간당 강수량
    private double windSpeed;       // 풍속
    private double temperature;     // 기온
    private double snowfall;        // 적설량

}