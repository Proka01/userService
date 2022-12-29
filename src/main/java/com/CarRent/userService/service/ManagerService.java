package com.CarRent.userService.service;

import com.CarRent.userService.dto.*;

public interface ManagerService {
    ManagerDto insertManager(ManagerRegisterDto managerRegisterDto);

    TokenResponseDto login(TokenRequestDto tokenRequestDto);

}
