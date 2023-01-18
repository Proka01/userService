package com.CarRent.userService.service;

import com.CarRent.userService.dto.*;

import java.sql.Date;

public interface ManagerService {
    ManagerDto insertManager(ManagerRegisterDto managerRegisterDto);
    ManagerDto findManagerById(Long id);
    TokenResponseDto login(TokenRequestDto tokenRequestDto);

    String updateUsername(Long id, String username);
    String updatePassword(Long id, String password);
    String updateEmail(Long id, String email);
    String updateFirstName(Long id, String firstName);
    String updateLastName(Long id, String lastName);
    String updatePhoneNumber(Long id, String phoneNumber);
    String updateBirthDate(Long id, Date birthDate);

    String updateCompanyName(Long id, String companyName);
}
