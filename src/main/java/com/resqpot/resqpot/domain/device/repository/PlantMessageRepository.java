package com.resqpot.resqpot.domain.device.repository;

import com.resqpot.resqpot.domain.device.entity.PlantMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PlantMessageRepository extends JpaRepository<PlantMessage, Long> {
    Optional<PlantMessage> findByDisasterTypeAndDangerLevel(String disasterType, Integer dangerLevel);
}