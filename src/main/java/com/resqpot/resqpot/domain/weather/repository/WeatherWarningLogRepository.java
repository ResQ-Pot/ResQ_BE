package com.resqpot.resqpot.domain.weather.repository;

import com.resqpot.resqpot.domain.weather.entity.WeatherWarningLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherWarningLogRepository extends JpaRepository<WeatherWarningLog, Long> {

    // 중복 저장 방지를 위해 uniqueKey 존재 여부를 확인합니다.
    boolean existsByUniqueKey(String uniqueKey);
}