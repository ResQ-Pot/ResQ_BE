// src/main/java/com/resqpot/resqpot/domain/disaster/entity/ActionGuide.java
package com.resqpot.resqpot.domain.disaster.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "action_guides")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActionGuide {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guide_id")
    private Long id;

    private String disasterType;
    private Integer targetDangerLevel;
    private String targetResidenceType;
    private String coreAction;

    @Column(columnDefinition = "TEXT")
    private String detailedDescription;

    @Builder
    public ActionGuide(String disasterType, Integer targetDangerLevel, String targetResidenceType, String coreAction, String detailedDescription) {
        this.disasterType = disasterType;
        this.targetDangerLevel = targetDangerLevel;
        this.targetResidenceType = targetResidenceType;
        this.coreAction = coreAction;
        this.detailedDescription = detailedDescription;
    }
}