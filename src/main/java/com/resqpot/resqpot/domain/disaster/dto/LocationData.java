// src/main/java/com/resqpot/resqpot/domain/disaster/dto/LocationData.java
package com.resqpot.resqpot.domain.disaster.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자 위치 기반의 환경 데이터를 잠깐 담아두는 DTO (DB 저장 X)
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationData {

    private int disasterHistoryScore;    // 재난 이력 점수 (0~10)
    private double distanceToMountainKm; // 가까운 산까지의 거리
    private double distanceToSeaKm;      // 가까운 바다까지의 거리

}