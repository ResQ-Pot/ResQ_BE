package com.resqpot.controller;

import com.resqpot.dto.ApiResponse;
import com.resqpot.dto.DisasterNotificationResponse;
import com.resqpot.service.DisasterNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/disasters")
@RequiredArgsConstructor
public class DisasterNotificationController {

    private final DisasterNotificationService notificationService;

    @GetMapping("/notifications")
    public ResponseEntity<ApiResponse<DisasterNotificationResponse>> getNotifications() {
        // TODO: JWT 연동 후 실제 userId로 교체
        DisasterNotificationResponse data = notificationService.getNotifications(1);
        return ResponseEntity.ok(ApiResponse.success(data));
    }
}