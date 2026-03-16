// src/main/java/com/resqpot/resqpot/domain/facility/entity/EmergencyFacility.java
package com.resqpot.resqpot.domain.facility.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "emergency_facilities")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmergencyFacility {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facility_id")
    private Long id;

    private String facilityType; // 기존 유지 (SHELTER, HOSPITAL 등)
    private String name;         // 기존 유지

    @Column(precision = 10, scale = 7)
    private BigDecimal latitude; // 기존 유지

    @Column(precision = 10, scale = 7)
    private BigDecimal longitude; // 기존 유지

    private String address;      // 기존 유지

    // --- 🌟 공공데이터 API에서 가져온 핵심 추가 필드들 ---

    @Column(name = "external_place_sn")
    private String externalPlaceSn; // 공공데이터 고유 일련번호 (데이터 갱신 시 필요)

    @Column(name = "detailed_position")
    private String detailedPosition; // 대피장소 상세위치 (예: "체육관 1층")

    private Integer capacity; // 수용 인원

    private Integer elevation; // 해발 고도 (홍수/해일 대비 핵심 데이터)

    @Column(name = "is_earthquake_resistant")
    private Boolean isEarthquakeResistant; // 내진 설계 적용 여부 (지진 대비 핵심 데이터)

    @Column(name = "is_active")
    private Boolean isActive; // 사용 여부 (현재 운영 중인지)

    @Builder
    public EmergencyFacility(String facilityType, String name, BigDecimal latitude, BigDecimal longitude, String address, String externalPlaceSn, String detailedPosition, Integer capacity, Integer elevation, Boolean isEarthquakeResistant, Boolean isActive) {
        this.facilityType = facilityType;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.externalPlaceSn = externalPlaceSn;
        this.detailedPosition = detailedPosition;
        this.capacity = capacity;
        this.elevation = elevation;
        this.isEarthquakeResistant = isEarthquakeResistant;
        this.isActive = isActive;
    }
}