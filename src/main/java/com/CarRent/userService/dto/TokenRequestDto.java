package com.CarRent.userService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRequestDto {
    private String username;
    private String password;
}
