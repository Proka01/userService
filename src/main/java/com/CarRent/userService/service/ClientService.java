package com.CarRent.userService.service;

import com.CarRent.userService.dto.ClientDto;
import com.CarRent.userService.dto.ClientRegisterDto;
import com.CarRent.userService.dto.TokenRequestDto;
import com.CarRent.userService.dto.TokenResponseDto;

import java.sql.Date;

public interface ClientService {
    ClientDto insertClient(ClientRegisterDto clientRegisterDto);
    ClientDto findClientById(Long id);
    TokenResponseDto login(TokenRequestDto tokenRequestDto);

    String verifyUser(String id);

    Long getDiscountForUser(Long id);

    String updateUserRentDays(Long id,Long numOfDays);

    String updateUsername(Long id, String username);
    String updatePassword(Long id, String password);
    String updateEmail(Long id, String email);
    String updateFirstName(Long id, String firstName);
    String updateLastName(Long id, String lastName);
    String updatePhoneNumber(Long id, String phoneNumber);
    String updateBirthDate(Long id, Date birthDate);
    String updatePasseportNumber(Long id, String passeportNumber);


}
