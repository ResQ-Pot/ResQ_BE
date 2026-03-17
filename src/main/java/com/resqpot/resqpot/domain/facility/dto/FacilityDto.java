// src/main/java/com/resqpot/resqpot/domain/facility/dto/FacilityDto.java
package com.resqpot.resqpot.domain.facility.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FacilityDto {

    private String id;              // 구글 Place ID 또는 내부 DB PK
    private String type;            // 타입 (SHELTER, HOSPITAL, PHARMACY)
    private String name;            // 장소명 (예: 안동 성소병원)
    private double lat;             // 위도
    private double lng;             // 경도
    private String address;         // 주소
    private int distanceMeters;     // 내 위치에서 떨어진 거리 (미터)
    private String extraInfo;       // 영업시간, 평점 등 부가 정보 (없으면 null)

}