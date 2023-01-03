package com.CarRent.userService.dto;

import com.CarRent.userService.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class ManagerDto {
    private Long id;

    //all users
    private String username;
    private String password;
    private String phoneNumber;
    private Date birthDate;
    private String firstName;
    private String lastName;
    private String email;

    private Role role;

    //manager
    private String companyName;
    private Date hireDate;
}
