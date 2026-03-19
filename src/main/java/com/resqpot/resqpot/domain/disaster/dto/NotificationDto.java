// 📂 src/main/java/com/resqpot/resqpot/domain/disaster/dto/NotificationDto.java
package com.resqpot.resqpot.domain.disaster.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NotificationDto {
    private Long notificationId;
    private String type;
    private String title;
    private String message;
    private String issuedAt;
    private String targetRegions;
}