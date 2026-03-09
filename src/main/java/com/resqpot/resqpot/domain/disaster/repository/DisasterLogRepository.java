// src/main/java/com/resqpot/resqpot/domain/disaster/repository/DisasterLogRepository.java
package com.resqpot.resqpot.domain.disaster.repository;

import com.resqpot.resqpot.domain.disaster.entity.DisasterLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisasterLogRepository extends JpaRepository<DisasterLog, Long> {
    // 행안부 일련번호(SN)로 이미 저장된 문자인지 확인하는 메서드
    boolean existsByApiSn(String apiSn);
}