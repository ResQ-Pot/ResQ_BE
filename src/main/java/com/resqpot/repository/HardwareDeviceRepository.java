package com.resqpot.repository;

import com.resqpot.domain.HardwareDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HardwareDeviceRepository extends JpaRepository<HardwareDevice, String> {

    Optional<HardwareDevice> findFirstByUserId(Integer userId);
}