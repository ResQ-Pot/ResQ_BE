// src/main/java/com/resqpot/resqpot/domain/disaster/service/DisasterPollingService.java
package com.resqpot.resqpot.domain.disaster.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.resqpot.resqpot.domain.disaster.entity.DisasterLog;
import com.resqpot.resqpot.domain.disaster.repository.DisasterLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class DisasterPollingService {

    private final DisasterLogRepository disasterLogRepository;
    private final RestTemplate restTemplate = new RestTemplate(); // 통신 마법사
    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON 파싱 마법사

    @Value("${safety.api.key}")
    private String serviceKey;

    // 1분마다(60초) 이 메서드가 자동으로 실행됩니다! (Polling)
    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void fetchAndSaveDisasterData() {
        try {
            log.info("📡 행안부 재난문자 API 폴링 시작...");

            // 1. 우아하게 URL 만들기 (샘플 코드의 urlBuilder 부분)
            URI uri = UriComponentsBuilder.fromUriString("https://www.safetydata.go.kr/V2/api/DSSP-IF-00247")
                    .queryParam("serviceKey", serviceKey)
                    .queryParam("pageNo", 1)
                    .queryParam("numOfRows", 50)
                    .queryParam("returnType", "json")
                    .build(true)
                    .toUri();

            // 2. API 호출 (샘플 코드의 HttpURLConnection 40줄이 이 1줄로 끝남!)
            String responseStr = restTemplate.getForObject(uri, String.class);

            // 3. JSON 껍데기 까서 데이터만 쏙쏙 뽑아내기
            JsonNode rootNode = objectMapper.readTree(responseStr);
            // 🚨 공공데이터는 보통 body 안에 데이터 배열이 들어있습니다. (응답 구조에 따라 수정 필요)
            JsonNode items = rootNode.path("body");

            if (items.isArray()) {
                for (JsonNode item : items) {
                    String apiSn = item.path("SN").asText();

                    // 4. 중복 검사 (이미 DB에 있는 일련번호면 무시!)
                    if (disasterLogRepository.existsByApiSn(apiSn)) {
                        continue;
                    }

                    // 5. 새로운 재난 발견! Entity로 만들어서 DB에 저장
                    DisasterLog newLog = DisasterLog.builder()
                            .apiSn(apiSn)
                            .disasterType(item.path("DST_SE_NM").asText())      // 재해구분명 (지진, 호우 등)
                            .dangerLevel(parseDangerLevel(item.path("EMRG_STEP_NM").asText())) // 긴급단계명 (위험도 숫자로 변환)
                            .targetArea(item.path("RCPTN_RGN_NM").asText())     // 수신지역
                            .rawMessage(item.path("MSG_CN").asText())           // 문자 내용 원본
                            .receivedAt(LocalDateTime.now())                    // 수신 시각
                            .build();

                    disasterLogRepository.save(newLog);
                    log.info("🚨 새로운 재난 저장 완료! 지역: {}, 종류: {}", newLog.getTargetArea(), newLog.getDisasterType());
                }
            }
        } catch (Exception e) {
            log.error("❌ 재난 데이터 가져오기 실패: {}", e.getMessage());
        }
    }

    // "위급재난" 같은 글씨를 1,2,3 숫자로 바꿔주는 헬퍼 메서드
    private Integer parseDangerLevel(String stepName) {
        if (stepName.contains("위급")) return 3; // 가장 위험 (빨간색 LED)
        if (stepName.contains("긴급")) return 2; // 중간 위험 (노란색 LED)
        return 1; // 안전 안내 (초록색 LED)
    }
}