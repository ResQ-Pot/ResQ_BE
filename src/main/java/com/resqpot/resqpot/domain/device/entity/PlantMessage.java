package com.resqpot.resqpot.domain.device.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "plant_messages")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlantMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long messageId;

    // 검색 조건 (매핑 키)
    @Column(name = "disaster_type", nullable = false, length = 50)
    private String disasterType; // TYPHOON, EARTHQUAKE, FLOOD, NORMAL 등

    @Column(name = "danger_level", nullable = false)
    private Integer dangerLevel; // 1~4 단계 (평상시는 0)

    // API 응답으로 나갈 실제 데이터
    @Column(name = "expression", nullable = false, length = 50)
    private String expression; // 하드웨어 표정 (SMILE, SCARED, CRY 등)

    @Column(name = "persona_message", nullable = false, columnDefinition = "TEXT")
    private String personaMessage; // 예: "비바람이 너무 거세서 무서워요!"

    @Builder
    public PlantMessage(String disasterType, Integer dangerLevel, String expression, String personaMessage) {
        this.disasterType = disasterType;
        this.dangerLevel = dangerLevel;
        this.expression = expression;
        this.personaMessage = personaMessage;
    }
}