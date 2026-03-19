// src/main/java/com/resqpot/resqpot/domain/disaster/repository/DisasterLogRepository.java
package com.resqpot.resqpot.domain.disaster.repository;

import com.resqpot.resqpot.domain.disaster.entity.DisasterLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DisasterLogRepository extends JpaRepository<DisasterLog, Long> {
    // 행안부 일련번호(SN)로 이미 저장된 문자인지 확인하는 메서드
    boolean existsByApiSn(String apiSn);
    // 수신시간(receivedAt) 기준 최신순 정렬해서 가져오기
    List<DisasterLog> findAllByOrderByReceivedAtDesc();
    Optional<DisasterLog> findTopByTargetAreaContainingOrderByReceivedAtDesc(String currentZone);
    List<DisasterLog> findByDisasterTypeNotOrderByReceivedAtDesc(String disasterType);
}