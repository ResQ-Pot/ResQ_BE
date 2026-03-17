package com.resqpot.repository;

import com.resqpot.domain.PlantMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlantMessageRepository extends JpaRepository<PlantMessage, Integer> {

    /**
     * 재난 유형과 위험 단계가 일치하는 메시지 조회
     */
    Optional<PlantMessage> findFirstByDisasterTypeAndDangerLevel(String disasterType, Integer dangerLevel);
}