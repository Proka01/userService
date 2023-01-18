package com.CarRent.userService.service;

import com.CarRent.userService.dto.ClientDto;
import com.CarRent.userService.dto.ClientRestrictDto;
import com.CarRent.userService.dto.TokenRequestDto;
import com.CarRent.userService.dto.TokenResponseDto;

import java.util.List;

public interface AdminService {

    ClientDto updateClientRestricted(ClientRestrictDto clientRestrictDto);

    TokenResponseDto login(TokenRequestDto tokenRequestDto);

    List<ClientDto> getAllClients();
}
