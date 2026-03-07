// src/main/java/com/resqpot/resqpot/domain/user/entity/UserPlant.java
package com.resqpot.resqpot.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_plants")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPlant {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plant_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String customItemsJson;

    @Builder
    public UserPlant(User user, String customItemsJson) {
        this.user = user;
        this.customItemsJson = customItemsJson;
    }
}