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

    @Column(unique = true)
    private String apiSn; // 행안부에서 주는 고유 일련번호 (중복 방지용)

    @Builder
    public DisasterLog(String disasterType, Integer dangerLevel, String targetArea, String rawMessage, LocalDateTime receivedAt, String apiSn) {
        this.disasterType = disasterType;
        this.dangerLevel = dangerLevel;
        this.targetArea = targetArea;
        this.rawMessage = rawMessage;
        this.receivedAt = receivedAt;
        this.apiSn = apiSn;
    }
}