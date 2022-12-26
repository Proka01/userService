package com.CarRent.userService.service;

import com.CarRent.userService.dto.ClientDto;
import com.CarRent.userService.dto.ClientRegisterDto;

public interface ClientService {
    ClientDto insertClient(ClientRegisterDto clientRegisterDto);
}
