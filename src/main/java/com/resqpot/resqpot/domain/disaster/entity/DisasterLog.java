// src/main/java/com/resqpot/resqpot/domain/disaster/entity/DisasterLog.java
package com.resqpot.resqpot.domain.disaster.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "disaster_logs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DisasterLog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long id;

    private String disasterType;
    private Integer dangerLevel;

    @Column(columnDefinition = "TEXT")
    private String targetArea;

    @Column(columnDefinition = "TEXT")
    private String rawMessage;

    private LocalDateTime receivedAt;

    @Builder
    public DisasterLog(String disasterType, Integer dangerLevel, String targetArea, String rawMessage, LocalDateTime receivedAt) {
        this.disasterType = disasterType;
        this.dangerLevel = dangerLevel;
        this.targetArea = targetArea;
        this.rawMessage = rawMessage;
        this.receivedAt = receivedAt;
    }
}