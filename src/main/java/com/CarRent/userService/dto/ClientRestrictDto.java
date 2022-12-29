package com.CarRent.userService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRestrictDto {
    private String username;
    private boolean restricted;
}
