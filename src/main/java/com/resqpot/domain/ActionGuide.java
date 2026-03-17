package com.resqpot.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "action_guides")
public class ActionGuide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guide_id")
    private Integer guideId;

    @Column(name = "disaster_type")
    private String disasterType;

    @Column(name = "danger_level")
    private Integer dangerLevel;

    @Column(name = "title")
    private String title;

    @Column(name = "recommended_action", columnDefinition = "TEXT")
    private String recommendedAction;
}