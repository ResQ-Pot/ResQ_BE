// src/main/java/com/resqpot/resqpot/domain/user/repository/UserProfileRepository.java
package com.resqpot.resqpot.domain.user.repository;

import com.resqpot.resqpot.domain.user.entity.UserProfile;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    // JpaRepository를 상속받기만 하면 findById, save 같은 기본 메서드가 공짜로 생깁니다!
Optional<UserProfile> findByUserId(Long userId);
}

