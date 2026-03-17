package com.resqpot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "disaster_logs")
public class DisasterLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Integer logId;

    @Column(name = "disaster_type")
    private String disasterType;

    @Column(name = "danger_level")
    private Integer dangerLevel;

    @Column(name = "target_area", columnDefinition = "TEXT")
    private String targetArea;

    @Column(name = "raw_message", columnDefinition = "TEXT")
    private String rawMessage;

    @Column(name = "received_at")
    private LocalDateTime receivedAt;
}