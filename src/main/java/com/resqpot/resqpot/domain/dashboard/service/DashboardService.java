// src/main/java/com/resqpot/resqpot/domain/dashboard/service/DashboardService.java
package com.resqpot.resqpot.domain.dashboard.service;

import com.resqpot.resqpot.domain.dashboard.dto.DashboardResponseDto;
import com.resqpot.resqpot.domain.dashboard.dto.DashboardResponseDto.*;
import com.resqpot.resqpot.domain.device.entity.HardwareDevice;
import com.resqpot.resqpot.domain.device.entity.PlantMessage;
import com.resqpot.resqpot.domain.disaster.client.WeatherApiClient;
import com.resqpot.resqpot.domain.disaster.dto.WeatherData;
import com.resqpot.resqpot.domain.disaster.entity.ActionGuide;
import com.resqpot.resqpot.domain.disaster.entity.DisasterLog;
import com.resqpot.resqpot.domain.disaster.repository.DisasterLogRepository;
import com.resqpot.resqpot.domain.user.entity.UserProfile;
import com.resqpot.resqpot.domain.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {

    private final UserProfileRepository userProfileRepository;
    private final DisasterLogRepository disasterLogRepository;
    private final WeatherApiClient weatherApiClient;

    /**
     * 메인 홈 대시보드 데이터 조회
     *
     * @param userId 인증된 사용자 ID (Security Context에서 추출)
     * @return DashboardResponseDto
     */
    public DashboardResponseDto getDashboard(Long userId) {

        // 1. 프로필 조회
        UserProfile profile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User profile not found for id: " + userId));

        String userName = profile.getUser() != null ? profile.getUser().getNickname() : null;

        // 2. 현재 날씨 조회 (기상청 API 연동)
        //    프로필에 저장된 마지막 위경도로 날씨 요청
        WeatherData weather = weatherApiClient.fetchCurrentWeather(
                profile.getLastLat(),
                profile.getLastLng()
        );

        // 3. 재난 상황 파악 (UserProfile의 위험도 점수 + 최신 DisasterLog)
        //    currentZone을 기준으로 가장 최근 재난 로그 조회
        Optional<DisasterLog> latestDisasterLog = disasterLogRepository
                .findTopByTargetAreaContainingOrderByReceivedAtDesc(profile.getCurrentZone());

        String statusName = resolveStatusName(profile, latestDisasterLog);
        int riskScore = profile.getCurrentRiskScore();

        // 4. 핵심 행동 요령 조회
        //    현재 위험도와 재난 유형에 맞는 가장 적합한 가이드 1건
        ActionGuideDto actionGuideDto = resolveActionGuide(profile, latestDisasterLog);

        // 5. 화분(하드웨어) 상태 조회
        PlantStatusDto plantStatusDto = resolvePlantStatus(userId, profile, latestDisasterLog);

        // 6. 응답 조립
        return DashboardResponseDto.builder()
                .userName(userName)
                .currentWeather(CurrentWeatherDto.builder()
                        .temperature(weather.getTemperature())
                        .precipitationProbability(weather.getPrecipitationProbability())
                        .windSpeed(weather.getWindSpeed())
                        .build())
                .disasterSituation(DisasterSituationDto.builder()
                        .statusName(statusName)
                        .riskScore(riskScore)
                        .build())
                .actionGuide(actionGuideDto)
                .plantStatus(plantStatusDto)
                .build();
    }

    // ── private helpers ──────────────────────────────────────────────────────

    /**
     * 재난 상황 이름 결정
     * - 활성 재난 로그가 있으면 "재난유형 + 특보등급" 조합
     * - 없으면 "이상 없음"
     */
    private String resolveStatusName(UserProfile profile, Optional<DisasterLog> latestLog) {
        if (latestLog.isEmpty() || "SAFE".equals(profile.getCurrentRiskLevel())) {
            return "이상 없음";
        }
        DisasterLog log = latestLog.get();
        // rawMessage 에서 특보 이름을 바로 꺼내거나, disasterType으로 레이블 구성
        return log.getDisasterType() + " " + resolveLevelLabel(log.getDangerLevel());
    }

    /** dangerLevel 숫자 → 한글 특보 등급 변환 */
    private String resolveLevelLabel(Integer dangerLevel) {
        if (dangerLevel == null) return "";
        return switch (dangerLevel) {
            case 1 -> "예비특보";
            case 2 -> "주의보";
            case 3 -> "경보";
            case 4 -> "심각";
            default -> "";
        };
    }

    /**
     * 행동 요령 결정
     * - 재난 발생 시: disasterType + dangerLevel에 맞는 ActionGuide
     * - 평상시: disasterType="NORMAL", dangerLevel=0
     */
    private ActionGuideDto resolveActionGuide(UserProfile profile, Optional<DisasterLog> latestLog) {
        String disasterType = "NORMAL";
        int dangerLevel = 0;
 
        if (latestLog.isPresent() && !"SAFE".equals(profile.getCurrentRiskLevel())) {
            disasterType = latestLog.get().getDisasterType();
            dangerLevel = Optional.ofNullable(latestLog.get().getDangerLevel()).orElse(1);
        }
 
        Optional<ActionGuide> guide = ActionGuide
                .findTopByDisasterTypeAndDangerLevel(disasterType, dangerLevel);
 
        return guide.map(g -> ActionGuideDto.builder()
                        .title(g.getTitle())
                        .recommendedAction(g.getRecommendedAction())
                        .build())
                .orElse(ActionGuideDto.builder()
                        .title("안전 유지")
                        .recommendedAction("현재 특이사항이 없습니다. 안전하게 생활하세요.")
                        .build());
    }

    /**
     * 화분 상태 결정
     * - HardwareDevice 존재 여부로 연결 상태 판단
     * - PlantMessage 테이블에서 disasterType + dangerLevel 기준으로 메시지 조회
     */

    private PlantStatusDto resolvePlantStatus(Long userId, UserProfile profile, Optional<DisasterLog> latestLog) {
        boolean isConnected = HardwareDevice.findByUserId(userId).isPresent();
 
        String disasterType = "NORMAL";
        int dangerLevel = 0;
 
        if (latestLog.isPresent() && !"SAFE".equals(profile.getCurrentRiskLevel())) {
            disasterType = latestLog.get().getDisasterType();
            dangerLevel = Optional.ofNullable(latestLog.get().getDangerLevel()).orElse(1);
        }
 
        Optional<PlantMessage> plantMessage = PlantMessage
                .findByDisasterTypeAndDangerLevel(disasterType, dangerLevel);
 
        return PlantStatusDto.builder()
                .isConnected(isConnected)
                .expression(plantMessage.map(PlantMessage::getExpression).orElse("SMILE"))
                .personaMessage(plantMessage.map(PlantMessage::getPersonaMessage).orElse("오늘도 안전한 하루 보내세요!"))
                .build();
    }
}