// src/main/java/com/resqpot/resqpot/domain/device/entity/HardwareDevice.java
package com.resqpot.resqpot.domain.device.entity;

import com.resqpot.resqpot.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "hardware_devices")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HardwareDevice {

    @Id
    @Column(name = "device_id") // DB 명세상 varchar이므로 자동 생성(Auto_increment) 쓰지 않음
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String currentExpression;
    private String displayText;
    private String ledColorStatus;
    private LocalDateTime lastSyncAt;

    @Builder
    public HardwareDevice(String id, User user, String currentExpression, String displayText, String ledColorStatus, LocalDateTime lastSyncAt) {
        this.id = id; // 고유 ID를 직접 주입받음
        this.user = user;
        this.currentExpression = currentExpression;
        this.displayText = displayText;
        this.ledColorStatus = ledColorStatus;
        this.lastSyncAt = lastSyncAt;
    }
}