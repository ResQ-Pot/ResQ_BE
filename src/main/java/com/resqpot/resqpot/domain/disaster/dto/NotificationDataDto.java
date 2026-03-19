// 📂 src/main/java/com/resqpot/resqpot/domain/disaster/dto/NotificationDataDto.java
package com.resqpot.resqpot.domain.disaster.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NotificationDataDto {
    private String currentSearchRegion;
    private int totalCount;
    private List<NotificationDto> notifications;
}