package com.CarRent.userService.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class ManagerRegisterDto {
    //all users
    private String username;
    private String password;
    private String phoneNumber;
    private Date birthDate;
    private String firstName;
    private String lastName;
    private String email;

    //manager
    private String companyName;
}
