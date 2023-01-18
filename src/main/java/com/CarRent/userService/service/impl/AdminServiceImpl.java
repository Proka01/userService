package com.CarRent.userService.service.impl;

import com.CarRent.userService.dto.ClientDto;
import com.CarRent.userService.dto.ClientRestrictDto;
import com.CarRent.userService.dto.TokenRequestDto;
import com.CarRent.userService.dto.TokenResponseDto;
import com.CarRent.userService.exception.NotFoundException;
import com.CarRent.userService.mapper.ClientMapper;
import com.CarRent.userService.model.User;
import com.CarRent.userService.repository.UserRepository;
import com.CarRent.userService.security.service.TokenService;
import com.CarRent.userService.service.AdminService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final ClientMapper clientMapper;
    private final TokenService tokenService;

    public AdminServiceImpl(UserRepository userRepository, ClientMapper clientMapper, TokenService tokenService) {
        this.userRepository = userRepository;
        this.clientMapper = clientMapper;
        this.tokenService = tokenService;
    }

    @Override
    public ClientDto updateClientRestricted(ClientRestrictDto clientRestrictDto) {
        User user = userRepository.findByUsername(clientRestrictDto.getUsername()).get();
        user.setRestricted(clientRestrictDto.isRestricted());
        userRepository.save(user);
        return clientMapper.clientToClientDto(user);
    }

    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        User user = userRepository
                .findUserByUsernameAndPasswordAndRole_Id(tokenRequestDto.getUsername(), tokenRequestDto.getPassword(), 3L)
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

    @Override
    public List<ClientDto> getAllClients() {
        List<User> userList = userRepository.findAllClients(    );
        List<ClientDto> clientDtoList = new ArrayList<>();

        for(User user : userList)
        {
            clientDtoList.add(clientMapper.clientToClientDto(user));
        }

        return clientDtoList;
    }
}
