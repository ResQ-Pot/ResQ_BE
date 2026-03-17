package com.resqpot.repository;

import com.resqpot.domain.ActionGuide;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActionGuideRepository extends JpaRepository<ActionGuide, Integer> {

    /**
     * 재난 유형과 위험 단계가 일치하는 행동 가이드 조회
     */
    Optional<ActionGuide> findFirstByDisasterTypeAndDangerLevel(String disasterType, Integer dangerLevel);
}