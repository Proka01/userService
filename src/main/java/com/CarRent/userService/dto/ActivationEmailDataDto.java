package com.CarRent.userService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivationEmailDataDto {
    private Long userId;
    private String notificationType;
    private String firstName;
    private String lastName;
    private String activationLink;
    private String activationCode;
    private String userEmail;

    public ActivationEmailDataDto(String notificationType, String firstName, String lastName, Long userId,String userEmail) {
        this.notificationType = notificationType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userId = userId;
        this.userEmail = userEmail;
    }
}
