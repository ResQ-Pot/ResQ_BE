// 📂 src/main/java/com/resqpot/resqpot/domain/disaster/service/DisasterNotificationService.java
package com.resqpot.resqpot.domain.disaster.service;

import com.resqpot.resqpot.domain.disaster.dto.NotificationDataDto;
import com.resqpot.resqpot.domain.disaster.dto.NotificationDto;
import com.resqpot.resqpot.domain.disaster.dto.NotificationResponseDto;
import com.resqpot.resqpot.domain.disaster.entity.DisasterLog;
import com.resqpot.resqpot.domain.disaster.repository.DisasterLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DisasterNotificationService {

    private final DisasterLogRepository disasterLogRepository;

    public NotificationResponseDto getDisasterNotifications(String searchRegion) {

        // 1. DB에서 재난문자 로그를 최신순으로 가져옵니다.
        List<DisasterLog> allLogs = disasterLogRepository.findAllByOrderByReceivedAtDesc();

        // 2. "내위치" 키워드 처리 (데모데이용 서울특별시 덮어쓰기)
        String actualTargetRegion = searchRegion;
        if ("내위치".equals(searchRegion)) {
            actualTargetRegion = "서울특별시"; // 데모데이 끝나면 DB 사용자 위치 조회로 변경!
        }

        final String finalRegionToFilter = actualTargetRegion;

        // 3. 필터링 및 DTO 변환
        List<NotificationDto> filteredList = allLogs.stream()
                .filter(log -> {
                    // 조건 1: 파라미터가 없으면 무조건 통과 (전체 보기)
                    if (finalRegionToFilter == null || finalRegionToFilter.trim().isEmpty()) {
                        return true;
                    }

                    // 조건 2: targetArea(예: "서울특별시 강남구, 경기도 성남시") 파싱 및 검사
                    String targetArea = log.getTargetArea();
                    if (targetArea == null || targetArea.isEmpty()) return false;

                    String[] regionArray = targetArea.split(",");
                    for (String r : regionArray) {
                        if (r.trim().startsWith(finalRegionToFilter)) {
                            return true; // "서울특별시"로 시작하는 지역이 있으면 통과!
                        }
                    }
                    return false;
                })
                .map(this::convertToDto) // DisasterLog 엔티티를 예쁜 DTO로 변환
                .limit(20) // 최대 20개
                .collect(Collectors.toList());

        // 4. 응답 DTO 조립
        NotificationDataDto dataDto = NotificationDataDto.builder()
                .currentSearchRegion(finalRegionToFilter == null ? "전체" : finalRegionToFilter)
                .totalCount(filteredList.size())
                .notifications(filteredList)
                .build();

        return NotificationResponseDto.builder()
                .status("success")
                .data(dataDto)
                .build();
    }

    // 🌟 Entity -> DTO 변환 도우미 메서드
    private NotificationDto convertToDto(DisasterLog log) {
        return NotificationDto.builder()
                .notificationId(log.getId())
                .type("DISASTER_TEXT") // 프론트엔드와 약속한 타입
                .title(log.getDisasterType() != null ? log.getDisasterType() + " 알림" : "재난 알림") // 예: "호우 알림"
                .message(log.getRawMessage())
                .issuedAt(log.getReceivedAt() != null ? log.getReceivedAt().toString() : "") // LocalDateTime을 문자열로
                .targetRegions(log.getTargetArea())
                .build();
    }
}