package com.resqpot.resqpot.domain.device.repository;

import com.resqpot.resqpot.domain.device.entity.HardwareDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface HardwareDeviceRepository extends JpaRepository<HardwareDevice, Long> {
    Optional<HardwareDevice> findByUserId(Long userId);
}