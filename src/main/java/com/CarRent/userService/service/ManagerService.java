package com.CarRent.userService.service;

import com.CarRent.userService.dto.ClientDto;
import com.CarRent.userService.dto.ManagerDto;
import com.CarRent.userService.dto.ManagerRegisterDto;

public interface ManagerService {
    ManagerDto insertManager(ManagerRegisterDto managerRegisterDto);
}
