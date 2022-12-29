package com.CarRent.userService.service;

import com.CarRent.userService.dto.ClientDto;
import com.CarRent.userService.dto.ClientRestrictDto;

public interface AdminService {

    ClientDto updateClientRestricted(ClientRestrictDto clientRestrictDto);

}
