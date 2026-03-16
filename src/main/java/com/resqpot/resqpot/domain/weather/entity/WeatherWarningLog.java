package com.resqpot.resqpot.domain.weather.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "weather_warning_logs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WeatherWarningLog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String title; // 특보 제목 (예: [기상청] 호우주의보 발표...)

    private String tmFc;  // 발표시각 (예: 202603091030)
    private String stnId; // 지점코드
    private String tmSeq; // 발표번호

    @Column(unique = true)
    private String uniqueKey; // 중복 방지용 조합 키 (tmFc_stnId_tmSeq)

    private String warVar;       // API 원본 종류 코드
    private String warLevel;     // API 원본 등급 코드
    private String warningType;  // 매핑된 한글 종류 (예: 호우)
    private String warningLevel; // 매핑된 한글 등급 (예: 주의보)

    @Builder
    public WeatherWarningLog(String title, String tmFc, String stnId, String tmSeq, String uniqueKey,
                             String warVar, String warLevel, String warningType, String warningLevel) {
        this.title = title;
        this.tmFc = tmFc;
        this.stnId = stnId;
        this.tmSeq = tmSeq;
        this.uniqueKey = uniqueKey;
        this.warVar = warVar;
        this.warLevel = warLevel;
        this.warningType = warningType;
        this.warningLevel = warningLevel;
    }
}