// 📂 src/main/java/com/resqpot/resqpot/domain/disaster/dto/NotificationResponseDto.java
package com.resqpot.resqpot.domain.disaster.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationResponseDto {
    private String status;
    private NotificationDataDto data;
}