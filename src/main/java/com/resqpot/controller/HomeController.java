package com.resqpot.controller;

import com.resqpot.dto.ApiResponse;
import com.resqpot.dto.DashboardResponse;
import com.resqpot.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/home")
@RequiredArgsConstructor
public class HomeController {

    private final DashboardService dashboardService;

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<DashboardResponse>> getDashboard() {

        DashboardResponse data = dashboardService.getDashboard(1,"test");
        return ResponseEntity.ok(ApiResponse.success(data));
    }
}