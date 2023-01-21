package com.CarRent.userService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangedEmailDTO {
    private Long userId;
    private String notificationType;
    private String firstName;
    private String lastName;
    private String password;
}
