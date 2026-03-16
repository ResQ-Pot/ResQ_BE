package com.resqpot.resqpot.domain.weather.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.resqpot.resqpot.domain.weather.constant.WarningType;
import com.resqpot.resqpot.domain.weather.entity.WeatherWarningLog;
import com.resqpot.resqpot.domain.weather.repository.WeatherWarningLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherPollingService {

    private final WeatherWarningLogRepository weatherLogRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${weather.api.key}")
    private String weatherServiceKey;

    @Scheduled(cron = "30 * * * * *")
    @Transactional
    public void fetchWeatherMessage() {
        try {
            log.info("📡 기상청 특보 통보문(getWthrWrnMsg) API 폴링 시작...");

            // 1. URL 생성 (getWthrWrnMsg 오퍼레이션 사용)
            // stnId=108(전국) 기준으로 가장 최근 통보문들을 가져옵니다.
            String urlString = String.format(
                    "http://apis.data.go.kr/1360000/WthrWrnInfoService/getWthrWrnMsg?serviceKey=%s&pageNo=1&numOfRows=10&dataType=JSON&stnId=108",
                    weatherServiceKey
            );

            URI uri = URI.create(urlString);
            String responseStr = restTemplate.getForObject(uri, String.class);

            // 2. JSON 파싱
            JsonNode rootNode = objectMapper.readTree(responseStr);
            JsonNode items = rootNode.path("response").path("body").path("items").path("item");

            if (items.isArray() && items.size() > 0) {
                for (JsonNode item : items) {
                    // API가 제공하는 정형 데이터 추출
                    String warVar = item.path("warVar").asText();     // 특보종류 코드 (1~12)
                    String warLevel = item.path("warLevel").asText(); // 특보등급 코드 (0:주의보, 1:경보)
                    String tmFc = item.path("tmFc").asText();         // 발표시각
                    String allWn = item.path("allWn").asText();       // 통보번호
                    String title = item.path("t1").asText();         // 통보문 제목 (예: [특보] 강풍주의보...)

                    // 3. 중복 방지를 위한 고유 키 생성
                    // 발표시각 + 종류 + 등급 + 번호 조합
                    String uniqueKey = String.format("%s_%s_%s_%s", tmFc, warVar, warLevel, allWn);

                    if (!weatherLogRepository.existsByUniqueKey(uniqueKey)) {
                        // Enum을 활용해 코드값을 읽기 좋은 텍스트로 변환
                        String typeDesc = WarningType.getDescription(warVar);
                        String levelDesc = warLevel.equals("1") ? "경보" : "주의보";

                        WeatherWarningLog newLog = WeatherWarningLog.builder()
                                .title(title)
                                .tmFc(tmFc)
                                .warVar(warVar)
                                .warLevel(warLevel)
                                .warningType(typeDesc)   // "호우"
                                .warningLevel(levelDesc) // "주의보"
                                .uniqueKey(uniqueKey)
                                .build();

                        weatherLogRepository.save(newLog);
                        log.info("✅ 신규 특보 저장: {} [{}-{}]", title, typeDesc, levelDesc);
                    }
                }
            } else {
                log.info("📡 현재 새로운 기상특보 통보문이 없습니다.");
            }

        } catch (Exception e) {
            log.error("❌ 통보문 API 수집 중 오류 발생: {}", e.getMessage());
        }
    }
}