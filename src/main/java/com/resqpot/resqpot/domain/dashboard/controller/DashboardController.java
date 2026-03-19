// src/main/java/com/resqpot/resqpot/domain/dashboard/controller/DashboardController.java
package com.resqpot.resqpot.domain.dashboard.controller;

import com.resqpot.resqpot.domain.dashboard.dto.DashboardResponseDto;
import com.resqpot.resqpot.domain.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/home")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    /**
     * GET /api/v1/home/dashboard
     *
     * 메인 홈 대시보드 조회
     * - 앱 메인 화면(탭 1) 진입 시
     * - 아래로 당겨서 새로고침(Pull-to-Refresh) 시
     *
     * @param userDetails JWT 인증 정보에서 추출한 사용자
     * @return 위험도 점수, 핵심 행동 요령, 현재 날씨, 화분 상태, 인사말
     */
    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponseDto> getDashboard(
            @RequestHeader("X-User-Id") Long userId
    ) {
        DashboardResponseDto data = dashboardService.getDashboard(userId);
        return ResponseEntity.ok(data);
    }
}