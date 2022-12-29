package com.CarRent.userService.service.impl;

import com.CarRent.userService.dto.ClientDto;
import com.CarRent.userService.dto.ClientRestrictDto;
import com.CarRent.userService.mapper.ClientMapper;
import com.CarRent.userService.model.User;
import com.CarRent.userService.repository.UserRepository;
import com.CarRent.userService.service.AdminService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final ClientMapper clientMapper;

    public AdminServiceImpl(UserRepository userRepository, ClientMapper clientMapper) {
        this.userRepository = userRepository;
        this.clientMapper = clientMapper;
    }

    @Override
    public ClientDto updateClientRestricted(ClientRestrictDto clientRestrictDto) {
        User user = userRepository.findByUsername(clientRestrictDto.getUsername());
        user.setRestricted(clientRestrictDto.isRestricted());
        userRepository.save(user);
        return clientMapper.clientToClientDto(user);
    }
}
