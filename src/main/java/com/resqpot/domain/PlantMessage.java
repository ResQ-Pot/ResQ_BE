package com.resqpot.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "plant_messages")
public class PlantMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Integer messageId;

    @Column(name = "disaster_type")
    private String disasterType;

    @Column(name = "danger_level")
    private Integer dangerLevel;

    @Column(name = "expression")
    private String expression;

    @Column(name = "persona_message", columnDefinition = "TEXT")
    private String personaMessage;
}