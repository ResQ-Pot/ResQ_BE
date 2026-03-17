package com.resqpot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "user_profiles")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Integer profileId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "residence_type")
    private String residenceType;

    @Column(name = "last_lat", precision = 10, scale = 7)
    private BigDecimal lastLat;

    @Column(name = "last_lng", precision = 10, scale = 7)
    private BigDecimal lastLng;

    @Column(name = "floor_info")
    private String floorInfo;

    @Column(name = "cohabitants_info")
    private String cohabitantsInfo;

    @Column(name = "special_notes", columnDefinition = "TEXT")
    private String specialNotes;

    @Column(name = "is_push_enabled")
    private Boolean isPushEnabled;

    @Column(name = "push_type_settings")
    private String pushTypeSettings;
}