package com.CarRent.userService.service.impl;

import com.CarRent.userService.dto.*;
import com.CarRent.userService.exception.NotFoundException;
import com.CarRent.userService.helper.MessageHelper;
import com.CarRent.userService.mapper.ClientMapper;
import com.CarRent.userService.model.User;
import com.CarRent.userService.repository.UserRepository;
import com.CarRent.userService.security.service.TokenService;
import com.CarRent.userService.service.ClientService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class ClientServiceImplementation implements ClientService {

    private UserRepository userRepository;
    private ClientMapper clientMapper;
    private MessageHelper messageHelper;
    private JmsTemplate jmsTemplate;
    private String destination;
    private final TokenService tokenService;

    public ClientServiceImplementation(UserRepository userRepository, ClientMapper clientMapper,
                                       TokenService tokenService, JmsTemplate jmsTemplate,
                                       @Value("${destination.createNotification}") String destination, MessageHelper messageHelper) {
        this.userRepository = userRepository;
        this.clientMapper = clientMapper;
        this.tokenService = tokenService;
        this.jmsTemplate = jmsTemplate;
        this.messageHelper = messageHelper;
        this.destination = destination;
    }

    @Override
    public ClientDto insertClient(ClientRegisterDto clientRegisterDto) {
        User user = clientMapper.clientRegisterDtoToUser(clientRegisterDto);

        Random rnd = new Random();
        String activationCode = String.valueOf(rnd.nextInt(999999));

        user.setActivationCode(activationCode);

        userRepository.save(user);
        Optional<User> userFromDB = userRepository.findByUsername(clientRegisterDto.getUsername());
        Long user_id = userFromDB.get().getId();
        String firstName = userFromDB.get().getFirstName();
        String lastName = userFromDB.get().getLastName();
        String email = userFromDB.get().getEmail();

        //sending to msg broker
        //TODO u liniji iznad user se upise, ali ga je potrebno procitati opet iz baze da bismo
        //TODO dohvatili njegov id koji se salje ka brokeru
        ActivationEmailDataDto activationEmailDataDto = new ActivationEmailDataDto("ACTIVATION_EMAIL",firstName,lastName,user_id,email);
        activationEmailDataDto.setActivationCode(activationCode);
        jmsTemplate.convertAndSend(destination, messageHelper.createTextMessage(activationEmailDataDto));

        return clientMapper.clientToClientDto(user);
    }

    @Override
    public ClientDto findClientById(Long id) {
        return userRepository.findById(id)
                .map(clientMapper::clientToClientDto)
                .orElseThrow(() -> new NotFoundException(String.format("Product with id: %d not found.", id)));
    }

    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        User user = userRepository
                .findUserByUsernameAndPasswordAndRole_Id(tokenRequestDto.getUsername(), tokenRequestDto.getPassword(), 1L)
                        .orElseThrow(() -> new NotFoundException(String
                        .format("User with username: %s and password: %s not found.", tokenRequestDto.getUsername(),
                                tokenRequestDto.getPassword())));

        if(user.getRestricted() || !user.getActivationCode().equals("verified")) return new TokenResponseDto("User restricted or not verified");

        //Create token payload
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("role", user.getRole().getName());
        //Generate token
        return new TokenResponseDto(tokenService.generate(claims));
    }

    @Override
    public String verifyUser(Long code) {

        User user = userRepository.findUserByActivationCode(code).orElseThrow(() -> new NotFoundException("Invalid activation code"));

        user.setActivationCode("verified");

        userRepository.save(user);

        return "Successfully verified";
    }
}
