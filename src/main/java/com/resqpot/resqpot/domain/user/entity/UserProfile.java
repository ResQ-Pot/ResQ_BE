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

    private Integer age;
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

    // 🌟 추가: 현재 사용자의 총 위험도 점수 (0~100)
    @Column(name = "current_risk_score")
    private Integer currentRiskScore = 0;

    // 🌟 추가: 위험 단계 (SAFE, WARNING, DANGER)
    @Column(name = "current_risk_level", length = 20)
    private String currentRiskLevel = "SAFE";

    @Builder
    public UserProfile(User user, Integer age, String residenceType, String floorInfo, String cohabitantsInfo, String specialNotes, BigDecimal lastLat, BigDecimal lastLng, String currentZone, Boolean isPushEnabled, String pushTypeSettings, Integer currentRiskScore, String currentRiskLevel) {
        this.user = user;
        this.age = age;
        this.residenceType = residenceType;
        this.floorInfo = floorInfo;
        this.cohabitantsInfo = cohabitantsInfo;
        this.specialNotes = specialNotes;
        this.lastLat = lastLat;
        this.lastLng = lastLng;
        this.currentZone = currentZone;
        this.isPushEnabled = isPushEnabled != null ? isPushEnabled : true;
        this.pushTypeSettings = pushTypeSettings;

        // 새로 추가된 필드의 null 처리 (기본값 세팅)
        this.currentRiskScore = currentRiskScore != null ? currentRiskScore : 0;
        this.currentRiskLevel = currentRiskLevel != null ? currentRiskLevel : "SAFE";
    }

    // 🌟 비즈니스 로직 편의 메서드: 위험도 갱신 시 사용
    public void updateRiskScoreAndLevel(int score, String level) {
        this.currentRiskScore = score;
        this.currentRiskLevel = level;
    }
}