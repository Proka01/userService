package com.CarRent.userService.service.impl;

import com.CarRent.userService.dto.ClientDto;
import com.CarRent.userService.dto.ManagerDto;
import com.CarRent.userService.dto.ManagerRegisterDto;
import com.CarRent.userService.mapper.ManagerMapper;
import com.CarRent.userService.model.User;
import com.CarRent.userService.repository.UserRepository;
import com.CarRent.userService.service.ManagerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ManagerServiceImpl implements ManagerService {

    private final ManagerMapper managerMapper;
    private final UserRepository userRepository;

    public ManagerServiceImpl(ManagerMapper managerMapper, UserRepository userRepository) {
        this.managerMapper = managerMapper;
        this.userRepository = userRepository;
    }

    @Override
    public ManagerDto insertManager(ManagerRegisterDto managerRegisterDto) {
        User user = managerMapper.managerRegisterDtoToUser(managerRegisterDto);
        userRepository.save(user);
        return managerMapper.managerToManagerDto(user);
    }
}
