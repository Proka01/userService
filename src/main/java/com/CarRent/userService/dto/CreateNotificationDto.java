package com.CarRent.userService.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class CreateNotificationDto {

    public CreateNotificationDto() {
    }

    public CreateNotificationDto(String notificationType, String embededMsg, Long userId, Long managerId, Date notificationDate) {
        this.notificationType = notificationType;
        this.embededMsg = embededMsg;
        this.userId = userId;
        this.managerId = managerId;
        this.notificationDate = notificationDate;
    }

    public CreateNotificationDto(String notificationType, String embededMsg, Long userId, Date notificationDate) {
        this.notificationType = notificationType;
        this.embededMsg = embededMsg;
        this.userId = userId;
        this.notificationDate = notificationDate;
    }

    public CreateNotificationDto(String notificationType, String embededMsg, Long userId) {
        this.notificationType = notificationType;
        this.embededMsg = embededMsg;
        this.userId = userId;
    }

    public CreateNotificationDto(String notificationType, String embededMsg, Long userId, Long managerId) {
        this.notificationType = notificationType;
        this.embededMsg = embededMsg;
        this.userId = userId;
        this.managerId = managerId;
    }


    private String notificationType;
    private String embededMsg;
    private Long userId;
    private Date notificationDate;
    private Long managerId;
}
