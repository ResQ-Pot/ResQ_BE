//TODO 주석 부분 날씨 API 연동 후 채워넣기
 

package com.resqpot.service;

import com.resqpot.domain.*;
import com.resqpot.dto.DashboardResponse;
import com.resqpot.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {

    private final UserProfileRepository userProfileRepository;
    private final HardwareDeviceRepository hardwareDeviceRepository;
    private final DisasterLogRepository disasterLogRepository;
    private final ActionGuideRepository actionGuideRepository;
    private final PlantMessageRepository plantMessageRepository;

    public DashboardResponse getDashboard(Integer userId, String userName) {

        // 1. 사용자 위치 조회 (날씨 API 연동 시 사용)
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 프로필을 찾을 수 없습니다."));

        // 2. 최신 재난 1건 (위험도 높은 것 우선)
        List<DisasterLog> logs = disasterLogRepository.findTopByOrderByDangerLevelDescReceivedAtDesc();
        DisasterLog latestLog = logs.isEmpty() ? null : logs.get(0);

        return DashboardResponse.builder()
                .userName(userName)
                .currentWeather(DashboardResponse.CurrentWeather.builder()
                        .temperature(null)               // TODO: 날씨 API 연동 시 채움
                        .precipitationProbability(null)
                        .windSpeed(null)
                        .build())
                .disasterSituation(buildDisasterSituation(latestLog))
                .actionGuide(buildActionGuide(latestLog))
                .plantStatus(buildPlantStatus(userId, latestLog))
                .build();
    }

    private DashboardResponse.DisasterSituation buildDisasterSituation(DisasterLog log) {
        if (log == null) {
            return DashboardResponse.DisasterSituation.builder()
                    .statusName("정상")
                    .riskScore(0)
                    .build();
        }
        // danger_level(1~5) → riskScore(20~100)
        int riskScore = Math.min(log.getDangerLevel() * 20, 100);
        String levelName = switch (log.getDangerLevel()) {
            case 1 -> "관심";
            case 2 -> "주의보";
            case 3 -> "경보";
            case 4 -> "위기";
            case 5 -> "긴급";
            default -> "알림";
        };
        return DashboardResponse.DisasterSituation.builder()
                .statusName(log.getDisasterType() + " " + levelName)
                .riskScore(riskScore)
                .build();
    }

    private DashboardResponse.ActionGuideDto buildActionGuide(DisasterLog log) {
        if (log == null) {
            return DashboardResponse.ActionGuideDto.builder()
                    .title("현재 안전한 상태")
                    .recommendedAction("특별한 행동 지침이 없습니다. 주변 상황을 계속 확인하세요.")
                    .build();
        }
        return actionGuideRepository
                .findFirstByDisasterTypeAndDangerLevel(log.getDisasterType(), log.getDangerLevel())
                .map(g -> DashboardResponse.ActionGuideDto.builder()
                        .title(g.getTitle())
                        .recommendedAction(g.getRecommendedAction())
                        .build())
                .orElseGet(() -> DashboardResponse.ActionGuideDto.builder()
                        .title(log.getDisasterType() + " 주의")
                        .recommendedAction("재난 상황을 주시하고 관계 기관의 안내를 따르세요.")
                        .build());
    }

    private DashboardResponse.PlantStatus buildPlantStatus(Integer userId, DisasterLog log) {
        Optional<HardwareDevice> deviceOpt = hardwareDeviceRepository.findFirstByUserId(userId);

        if (deviceOpt.isEmpty()) {
            return DashboardResponse.PlantStatus.builder()
                    .isConnected(false)
                    .build();
        }

        HardwareDevice device = deviceOpt.get();

        if (log == null) {
            return DashboardResponse.PlantStatus.builder()
                    .isConnected(true)
                    .expression(device.getCurrentExpression())
                    .personaMessage(device.getDisplayText())
                    .build();
        }

        return plantMessageRepository
                .findFirstByDisasterTypeAndDangerLevel(log.getDisasterType(), log.getDangerLevel())
                .map(m -> DashboardResponse.PlantStatus.builder()
                        .isConnected(true)
                        .expression(m.getExpression())
                        .personaMessage(m.getPersonaMessage())
                        .build())
                .orElseGet(() -> DashboardResponse.PlantStatus.builder()
                        .isConnected(true)
                        .expression(device.getCurrentExpression())
                        .personaMessage(device.getDisplayText())
                        .build());
    }
}