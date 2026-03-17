package com.resqpot.service;

import com.resqpot.domain.DisasterLog;
import com.resqpot.domain.UserProfile;
import com.resqpot.dto.DisasterNotificationResponse;
import com.resqpot.repository.DisasterLogRepository;
import com.resqpot.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DisasterNotificationService {

    private static final DateTimeFormatter ISO_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private final DisasterLogRepository disasterLogRepository;
    private final UserProfileRepository userProfileRepository;

    public DisasterNotificationResponse getNotifications(Integer userId) {

        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 프로필을 찾을 수 없습니다."));

        // residence_type을 지역명으로 사용, 없으면 전체 조회
        String region = profile.getResidenceType();

        List<DisasterLog> logs;
        String searchRegion;

        if (region == null || region.isBlank()) {
            logs = disasterLogRepository.findTop10OrderByReceivedAtDesc();
            searchRegion = "전국";
        } else {
            logs = disasterLogRepository.findTop10ByTargetAreaContaining(region);
            if (logs.isEmpty()) {
                log.info("지역 '{}' 재난 로그 없음, 전체 10건 반환", region);
                logs = disasterLogRepository.findTop10OrderByReceivedAtDesc();
            }
            searchRegion = region;
        }

        List<DisasterNotificationResponse.NotificationItem> items = logs.stream()
                .map(this::toItem)
                .toList();

        return DisasterNotificationResponse.builder()
                .currentSearchRegion(searchRegion)
                .totalCount(items.size())
                .notifications(items)
                .build();
    }

    private DisasterNotificationResponse.NotificationItem toItem(DisasterLog log) {
        String issuedAt = log.getReceivedAt() != null
                ? log.getReceivedAt().atOffset(ZoneOffset.UTC).format(ISO_FORMATTER)
                : null;

        String levelName = switch (log.getDangerLevel() == null ? 0 : log.getDangerLevel()) {
            case 1 -> "관심";
            case 2 -> "주의보";
            case 3 -> "경보";
            case 4 -> "위기";
            case 5 -> "긴급";
            default -> "알림";
        };
        String title = (log.getDisasterType() != null ? log.getDisasterType() : "재난") + " " + levelName;

        return DisasterNotificationResponse.NotificationItem.builder()
                .notificationId(log.getLogId())
                .type("DISASTER_TEXT")
                .title(title)
                .message(log.getRawMessage())
                .issuedAt(issuedAt)
                .targetRegions(log.getTargetArea())
                .build();
    }
}