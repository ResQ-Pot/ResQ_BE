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
    private Long id; // 기존 스타일대로 변수명은 id 유지

    private String disasterType;
    private Integer dangerLevel; // DBML에 맞춰 targetDangerLevel -> dangerLevel
    private String title;        // DBML에 맞춰 coreAction -> title

    @Column(columnDefinition = "TEXT")
    private String recommendedAction; // DBML에 맞춰 detailedDescription -> recommendedAction

    @Builder
    public ActionGuide(String disasterType, Integer dangerLevel, String title, String recommendedAction) {
        this.disasterType = disasterType;
        this.dangerLevel = dangerLevel;
        this.title = title;
        this.recommendedAction = recommendedAction;
    }
}