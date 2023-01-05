package com.CarRent.userService.service;

import com.CarRent.userService.dto.ClientDto;
import com.CarRent.userService.dto.ClientRegisterDto;
import com.CarRent.userService.dto.TokenRequestDto;
import com.CarRent.userService.dto.TokenResponseDto;

public interface ClientService {
    ClientDto insertClient(ClientRegisterDto clientRegisterDto);
    ClientDto findClientById(Long id);
    TokenResponseDto login(TokenRequestDto tokenRequestDto);

    String verifyUser(String id);
}
