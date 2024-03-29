package com.CarRent.userService.service.impl;

import com.CarRent.userService.dto.*;
import com.CarRent.userService.exception.NotFoundException;
import com.CarRent.userService.helper.MessageHelper;
import com.CarRent.userService.mapper.ManagerMapper;
import com.CarRent.userService.model.User;
import com.CarRent.userService.repository.UserRepository;
import com.CarRent.userService.security.service.TokenService;
import com.CarRent.userService.service.ManagerService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Optional;

@Service
@Transactional
public class ManagerServiceImpl implements ManagerService {

    private final ManagerMapper managerMapper;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private MessageHelper messageHelper;
    private JmsTemplate jmsTemplate;
    private String destination;

    public ManagerServiceImpl(ManagerMapper managerMapper, UserRepository userRepository, TokenService tokenService,
                              JmsTemplate jmsTemplate,
                              @Value("${destination.createNotification}") String destination, MessageHelper messageHelper) {
        this.managerMapper = managerMapper;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.jmsTemplate = jmsTemplate;
        this.messageHelper = messageHelper;
        this.destination = destination;
    }

    @Override
    public ManagerDto insertManager(ManagerRegisterDto managerRegisterDto) {
        User user = managerMapper.managerRegisterDtoToUser(managerRegisterDto);
        userRepository.save(user);
        Optional<User> userFromDB = userRepository.findByUsername(managerRegisterDto.getUsername());
        Long user_id = userFromDB.get().getId();
        String firstName = userFromDB.get().getFirstName();
        String lastName = userFromDB.get().getLastName();
        String email = userFromDB.get().getEmail();

        //sending to msg broker
        //TODO u liniji iznad user se upise, ali ga je potrebno procitati opet iz baze da bismo
        //TODO dohvatili njegov id koji se salje ka brokeru
        ActivationEmailDataDto activationEmailDataDto = new ActivationEmailDataDto("ACTIVATION_EMAIL",firstName,lastName,user_id,email);
        jmsTemplate.convertAndSend(destination, messageHelper.createTextMessage(activationEmailDataDto));

        return managerMapper.managerToManagerDto(user);
    }

    @Override
    public ManagerDto findManagerById(Long id) {
        return userRepository.findById(id)
                .map(managerMapper::managerToManagerDto)
                .orElseThrow(() -> new NotFoundException(String.format("Product with id: %d not found.", id)));
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

    @Override
    public String updateUsername(Long id, String username) {
        User user = userRepository.findById(id).get();
        user.setUsername(username);
        userRepository.save(user);
        return "Successfully updated username";
    }

    @Override
    public String updatePassword(Long id, String password) {
        User user = userRepository.findById(id).get();
        user.setPassword(password);
        userRepository.save(user);
        return "Successfully updated password";
    }

    @Override
    public String updateEmail(Long id, String email) {
        User user = userRepository.findById(id).get();
        user.setEmail(email);
        userRepository.save(user);
        return "Successfully updated email";
    }

    @Override
    public String updateFirstName(Long id, String firstName) {
        User user = userRepository.findById(id).get();
        user.setFirstName(firstName);
        userRepository.save(user);
        return "Successfully updated firstName";
    }

    @Override
    public String updateLastName(Long id, String lastName) {
        User user = userRepository.findById(id).get();
        user.setLastName(lastName);
        userRepository.save(user);
        return "Successfully updated lastName";
    }

    @Override
    public String updatePhoneNumber(Long id, String phoneNumber) {
        User user = userRepository.findById(id).get();
        user.setPhoneNumber(phoneNumber);
        userRepository.save(user);
        return "Successfully updated phone number";
    }

    @Override
    public String updateBirthDate(Long id, Date birthDate) {
        User user = userRepository.findById(id).get();
        user.setBirthDate(birthDate);
        userRepository.save(user);
        return "Successfully updated birth date";
    }

    @Override
    public String updateCompanyName(Long id, String companyName) {
        User user = userRepository.findById(id).get();
        user.setCompanyName(companyName);
        userRepository.save(user);
        return "Successfully updated company name";
    }
}
