package com.CarRent.userService.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@Entity
@Table(indexes = {@Index(columnList = "username", unique = true), @Index(columnList = "passportNumber", unique = true)})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //all users
    private String username;
    private String password;
    private String phoneNumber;
    private Date birthDate;
    private String firstName;
    private String lastName;
    private String email;

    @ManyToOne
    private Role role;

    //client
    private String passportNumber;
    private Long rentDaysNumber;
    private Boolean restricted;
    private String activationCode;

    //manager
    private String companyName;
    private Date hireDate;

}
