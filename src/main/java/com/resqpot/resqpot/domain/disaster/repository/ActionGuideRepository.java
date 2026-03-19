package com.resqpot.resqpot.domain.disaster.repository;

import com.resqpot.resqpot.domain.disaster.entity.ActionGuide;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActionGuideRepository extends JpaRepository<ActionGuide, Long> {
    Optional<ActionGuide> findTopByDisasterTypeAndDangerLevel(String disasterType, Integer dangerLevel);
}