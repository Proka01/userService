package com.CarRent.userService.service.impl;

import com.CarRent.userService.dto.ClientDto;
import com.CarRent.userService.dto.ClientRegisterDto;
import com.CarRent.userService.dto.TokenRequestDto;
import com.CarRent.userService.dto.TokenResponseDto;
import com.CarRent.userService.exception.NotFoundException;
import com.CarRent.userService.mapper.ClientMapper;
import com.CarRent.userService.model.User;
import com.CarRent.userService.repository.UserRepository;
import com.CarRent.userService.security.TokenService;
import com.CarRent.userService.service.ClientService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClientServiceImplementation implements ClientService {

    private UserRepository userRepository;
    private ClientMapper clientMapper;
    private final TokenService tokenService;

    public ClientServiceImplementation(UserRepository userRepository, ClientMapper clientMapper, TokenService tokenService) {
        this.userRepository = userRepository;
        this.clientMapper = clientMapper;
        this.tokenService = tokenService;
    }

    @Override
    public ClientDto insertClient(ClientRegisterDto clientRegisterDto) {
        User user = clientMapper.clientRegisterDtoToUser(clientRegisterDto);
        userRepository.save(user);
        return clientMapper.clientToClientDto(user);
    }

    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        User user = userRepository
                .findUserByUsernameAndPassword(tokenRequestDto.getUsername(), tokenRequestDto.getPassword())
                        .orElseThrow(() -> new NotFoundException(String
                        .format("User with username: %s and password: %s not found.", tokenRequestDto.getUsername(),
                                tokenRequestDto.getPassword())));
        //Create token payload
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("role", user.getRole().getName());
        //Generate token
        return new TokenResponseDto(tokenService.generate(claims));
    }
}
