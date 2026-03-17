package com.resqpot.repository;

import com.resqpot.domain.DisasterLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface DisasterLogRepository extends JpaRepository<DisasterLog, Integer> {

    @Query("SELECT d FROM DisasterLog d ORDER BY d.dangerLevel DESC, d.receivedAt DESC")
    List<DisasterLog> findTopByOrderByDangerLevelDescReceivedAtDesc();

    @Query(value = "SELECT * FROM disaster_logs WHERE target_area LIKE CONCAT('%', :region, '%') ORDER BY received_at DESC LIMIT 10", nativeQuery = true)
    List<DisasterLog> findTop10ByTargetAreaContaining(@Param("region") String region);

    @Query(value = "SELECT * FROM disaster_logs ORDER BY received_at DESC LIMIT 10", nativeQuery = true)
    List<DisasterLog> findTop10OrderByReceivedAtDesc();
}