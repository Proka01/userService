package com.CarRent.userService.service;

import com.CarRent.userService.dto.UserRankCreateDto;
import com.CarRent.userService.dto.UserRankUpdateDto;

public interface UserRankService {

    String update(UserRankUpdateDto userRankUpdateDto);

    String create(UserRankCreateDto userRankCreateDto);

    String delete(Long id);
}
