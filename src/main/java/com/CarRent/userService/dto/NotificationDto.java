package com.CarRent.userService.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class NotificationDto {
    public NotificationDto() {
    }

    public NotificationDto(String notificationType, String embededMsg, Long userId, Long managerId, Date notificationDate) {
        this.notificationType = notificationType;
        this.embededMsg = embededMsg;
        this.userId = userId;
        this.managerId = managerId;
        this.notificationDate = notificationDate;
    }

    public NotificationDto(String notificationType, String embededMsg, Long userId, Date notificationDate) {
        this.notificationType = notificationType;
        this.embededMsg = embededMsg;
        this.userId = userId;
        this.notificationDate = notificationDate;
    }

    public NotificationDto(String notificationType, String embededMsg, Long userId) {
        this.notificationType = notificationType;
        this.embededMsg = embededMsg;
        this.userId = userId;
    }

    private Long id;

    private String notificationType;
    private String embededMsg;
    private Long userId;
    private Long managerId;
    private Date notificationDate;
}
