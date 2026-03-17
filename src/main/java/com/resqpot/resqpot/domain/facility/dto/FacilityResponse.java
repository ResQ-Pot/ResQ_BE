// src/main/java/com/resqpot/resqpot/domain/facility/dto/FacilityResponse.java
package com.resqpot.resqpot.domain.facility.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FacilityResponse {

    private CenterLocation centerLocation; // 검색 기준이 된 내 위치
    private int totalCount;                // 검색된 총 시설 개수
    private List<FacilityDto> facilities;  // 병원, 약국, 대피소가 짬뽕된 리스트

    // 내부 클래스로 깔끔하게 위경도 묶기
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CenterLocation {
        private double lat;
        private double lng;
    }
}