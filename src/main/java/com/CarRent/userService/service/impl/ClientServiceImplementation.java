package com.CarRent.userService.service.impl;

import com.CarRent.userService.dto.ClientDto;
import com.CarRent.userService.dto.ClientRegisterDto;
import com.CarRent.userService.mapper.ClientMapper;
import com.CarRent.userService.model.User;
import com.CarRent.userService.repository.UserRepository;
import com.CarRent.userService.service.ClientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClientServiceImplementation implements ClientService {

    private UserRepository userRepository;
    private ClientMapper clientMapper;

    public ClientServiceImplementation(UserRepository userRepository, ClientMapper clientMapper) {
        this.userRepository = userRepository;
        this.clientMapper = clientMapper;
    }

    @Override
    public ClientDto insertClient(ClientRegisterDto clientRegisterDto) {
        User user = clientMapper.clientRegisterDtoToUser(clientRegisterDto);
        userRepository.save(user);
        return clientMapper.clientToClientDto(user);
    }
}
