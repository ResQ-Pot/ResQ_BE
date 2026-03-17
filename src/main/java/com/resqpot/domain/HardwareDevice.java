package com.resqpot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "hardware_devices")
public class HardwareDevice {

    @Id
    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "current_expression")
    private String currentExpression;

    @Column(name = "display_text")
    private String displayText;

    @Column(name = "led_color_status")
    private String ledColorStatus;

    @Column(name = "last_sync_at")
    private LocalDateTime lastSyncAt;
}