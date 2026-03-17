// src/main/java/com/resqpot/resqpot/domain/facility/controller/FacilityController.java
package com.resqpot.resqpot.domain.facility.controller;

import com.resqpot.resqpot.domain.facility.dto.FacilityResponse;
import com.resqpot.resqpot.domain.facility.service.FacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FacilityController {

    private final FacilityService facilityService;

    /**
     * 내 주변 병원, 약국, 대피소 통합 조회 API
     * GET /api/v1/facilities?lat=36.5684&lng=128.7295&radius=1000
     */
    @GetMapping("/api/v1/facilities")
    public ResponseEntity<FacilityResponse> getNearbyFacilities(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam(defaultValue = "1000") int radius // 반경 안 보내면 기본값 1km(1000m)
    ) {
        // 서비스(총괄 매니저)한테 일 시키기
        FacilityResponse response = facilityService.getAllNearbyFacilities(lat, lng, radius);

        // 결과(JSON)를 프론트로 발사!
        return ResponseEntity.ok(response);
    }
}