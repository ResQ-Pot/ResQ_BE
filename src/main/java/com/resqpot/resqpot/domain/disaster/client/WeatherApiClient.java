// src/main/java/com/resqpot/resqpot/domain/disaster/client/WeatherApiClient.java
package com.resqpot.resqpot.domain.disaster.client;

import com.resqpot.resqpot.domain.disaster.dto.WeatherData;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class WeatherApiClient {

    public WeatherData fetchCurrentWeather(BigDecimal lat, BigDecimal lng) {
        // [데모데이 전용 Mock 데이터]
        // 시나리오: "엄청난 태풍이 오고 있는 상황"을 가정하여 강수량과 풍속을 극단적으로 높입니다.
        return WeatherData.builder()
                .rainfallPerHour(85.0) // 80mm 이상 (위험도 9점)
                .windSpeed(20.0)       // 19m/s 이상 (위험도 8점)
                .temperature(22.0)     // 정상 기온 (위험도 0점)
                .snowfall(0.0)         // 눈 안 옴 (위험도 0점)
                .build();
    }
}