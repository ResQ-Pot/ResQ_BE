// src/main/java/com/resqpot/resqpot/domain/user/entity/UserProfile.java
package com.resqpot.resqpot.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "user_profiles")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String residenceType;
    private String floorInfo;
    private String cohabitantsInfo;

    @Column(columnDefinition = "TEXT")
    private String specialNotes;

    @Column(precision = 10, scale = 7)
    private BigDecimal lastLat;

    @Column(precision = 10, scale = 7)
    private BigDecimal lastLng;

    @Column(name = "current_zone", length = 100)
    private String currentZone;

    @Column(name = "is_push_enabled")
    private Boolean isPushEnabled = true;

    private String pushTypeSettings;

    @Builder
    public UserProfile(User user, String residenceType, String floorInfo, String cohabitantsInfo, String specialNotes, BigDecimal lastLat, BigDecimal lastLng, String currentZone, Boolean isPushEnabled, String pushTypeSettings) {
        this.user = user;
        this.residenceType = residenceType;
        this.floorInfo = floorInfo;
        this.cohabitantsInfo = cohabitantsInfo;
        this.specialNotes = specialNotes;
        this.lastLat = lastLat;
        this.lastLng = lastLng;
        this.currentZone = currentZone;
        this.isPushEnabled = isPushEnabled != null ? isPushEnabled : true;
        this.pushTypeSettings = pushTypeSettings;
    }
}