package com.resqpot.resqpot.domain.weather.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum WeatherStation {
    SEOUL_ALL("108", "서울", "전국"),
    SEOUL_METRO("109", "서울", "서울, 인천, 경기도"),
    BUSAN("159", "부산", "부산, 울산, 경상남도"),
    DAEGU("143", "대구", "대구, 경상북도"),
    GWANGJU("156", "광주", "광주, 전라남도"),
    JEONJU("146", "전주", "전북자치도"),
    DAEJEON("133", "대전", "대전, 세종, 충청남도"),
    CHEONGJU("131", "청주", "충청북도"),
    GANGNEUNG("105", "강릉", "강원도"),
    JEJU("184", "제주", "제주도");

    private final String stnId;
    private final String locationName;
    private final String coverage;

    // stnId 숫자 값으로 Enum 객체를 찾아주는 유틸리티 메서드
    public static WeatherStation findByStnId(String stnId) {
        return Arrays.stream(WeatherStation.values())
                .filter(station -> station.getStnId().equals(stnId))
                .findFirst()
                .orElse(null); // 없는 코드일 경우 대비
    }
}