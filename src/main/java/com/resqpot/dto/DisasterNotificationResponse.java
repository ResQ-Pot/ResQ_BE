package com.resqpot.dto;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class DisasterNotificationResponse {

    private String currentSearchRegion;
    private Integer totalCount;
    private List<NotificationItem> notifications;

    @Getter @Builder
    public static class NotificationItem {
        private Integer notificationId;
        private String type;
        private String title;
        private String message;
        private String issuedAt;
        private String targetRegions;
    }
}