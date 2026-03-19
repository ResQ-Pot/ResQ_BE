// 📂 src/main/java/com/resqpot/resqpot/domain/disaster/controller/DisasterNotificationController.java
package com.resqpot.resqpot.domain.disaster.controller;

import com.resqpot.resqpot.domain.disaster.dto.NotificationResponseDto;
import com.resqpot.resqpot.domain.disaster.service.DisasterNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class DisasterNotificationController {

    private final DisasterNotificationService notificationService;

    @GetMapping("/notifications")
    public ResponseEntity<NotificationResponseDto> getNotifications(
            @RequestParam(required = false) String region
    ) {
        NotificationResponseDto response = notificationService.getDisasterNotifications(region);
        return ResponseEntity.ok(response);
    }
}