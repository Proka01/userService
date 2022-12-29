package com.CarRent.userService.service.impl;

import com.CarRent.userService.dto.*;
import com.CarRent.userService.exception.NotFoundException;
import com.CarRent.userService.mapper.ManagerMapper;
import com.CarRent.userService.model.User;
import com.CarRent.userService.repository.UserRepository;
import com.CarRent.userService.security.service.TokenService;
import com.CarRent.userService.service.ManagerService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ManagerServiceImpl implements ManagerService {

    private final ManagerMapper managerMapper;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public ManagerServiceImpl(ManagerMapper managerMapper, UserRepository userRepository, TokenService tokenService) {
        this.managerMapper = managerMapper;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Override
    public ManagerDto insertManager(ManagerRegisterDto managerRegisterDto) {
        User user = managerMapper.managerRegisterDtoToUser(managerRegisterDto);
        userRepository.save(user);
        return managerMapper.managerToManagerDto(user);
    }

    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        User user = userRepository
                .findUserByUsernameAndPasswordAndRole_Id(tokenRequestDto.getUsername(), tokenRequestDto.getPassword(), 2L)
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
