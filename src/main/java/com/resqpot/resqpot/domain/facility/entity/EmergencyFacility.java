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

    private String facilityType;
    private String name;

    @Column(precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(precision = 10, scale = 7)
    private BigDecimal longitude;

    private String address;

    @Builder
    public EmergencyFacility(String facilityType, String name, BigDecimal latitude, BigDecimal longitude, String address) {
        this.facilityType = facilityType;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }
}