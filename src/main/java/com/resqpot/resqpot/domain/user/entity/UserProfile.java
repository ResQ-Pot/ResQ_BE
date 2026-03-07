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

    @OneToOne(fetch = FetchType.LAZY) // 1:1 관계 명시
    @JoinColumn(name = "user_id")
    private User user;

    private String residenceType;
    private String floorInfo;
    private String cohabitantsInfo;

    @Column(columnDefinition = "TEXT")
    private String specialNotes;

    @Column(precision = 10, scale = 7) // 위경도는 BigDecimal 권장
    private BigDecimal lastLat;

    @Column(precision = 10, scale = 7)
    private BigDecimal lastLng;

    @Column(name = "is_push_enabled")
    private Boolean isPushEnabled = true;

    private String pushTypeSettings;

    @Builder
    public UserProfile(User user, String residenceType, String floorInfo, String cohabitantsInfo, String specialNotes, BigDecimal lastLat, BigDecimal lastLng, Boolean isPushEnabled, String pushTypeSettings) {
        this.user = user;
        this.residenceType = residenceType;
        this.floorInfo = floorInfo;
        this.cohabitantsInfo = cohabitantsInfo;
        this.specialNotes = specialNotes;
        this.lastLat = lastLat;
        this.lastLng = lastLng;
        this.isPushEnabled = isPushEnabled != null ? isPushEnabled : true;
        this.pushTypeSettings = pushTypeSettings;
    }
}