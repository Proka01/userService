package com.CarRent.userService.service;

import com.CarRent.userService.dto.*;

public interface ManagerService {
    ManagerDto insertManager(ManagerRegisterDto managerRegisterDto);
    ManagerDto findManagerById(Long id);
    TokenResponseDto login(TokenRequestDto tokenRequestDto);

}
