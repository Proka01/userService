package com.CarRent.userService.service.impl;

import com.CarRent.userService.dto.*;
import com.CarRent.userService.exception.NotFoundException;
import com.CarRent.userService.helper.MessageHelper;
import com.CarRent.userService.mapper.ClientMapper;
import com.CarRent.userService.model.User;
import com.CarRent.userService.model.UserRank;
import com.CarRent.userService.repository.UserRankRepository;
import com.CarRent.userService.repository.UserRepository;
import com.CarRent.userService.security.service.TokenService;
import com.CarRent.userService.service.ClientService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
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
    private String destinationForPassword;
    private final TokenService tokenService;
    private final UserRankRepository userRankRepository;

    public ClientServiceImplementation(UserRepository userRepository, ClientMapper clientMapper,
                                       TokenService tokenService, JmsTemplate jmsTemplate, @Value("${destination.passwordChanged}") String destinationForPassword,
                                       @Value("${destination.createNotification}") String destination, MessageHelper messageHelper, UserRankRepository userRankRepository) {
        this.userRepository = userRepository;
        this.clientMapper = clientMapper;
        this.tokenService = tokenService;
        this.jmsTemplate = jmsTemplate;
        this.messageHelper = messageHelper;
        this.destination = destination;
        this.destinationForPassword = destinationForPassword;
        this.userRankRepository = userRankRepository;
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
        activationEmailDataDto.setActivationLink(" http://localhost:8085/api/client/verify/"+activationCode);
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
    public String verifyUser(String code) {

        User user = userRepository.findUserByActivationCode(code).orElseThrow(() -> new NotFoundException("Invalid activation code"));

        user.setActivationCode("verified");

        userRepository.save(user);

        return "Successfully verified";
    }

    @Override
    public Long getDiscountForUser(Long id) {

        User user = userRepository.findById(id).get();

        UserRank rank = userRankRepository.findRankForUser(user.getRentDaysNumber()).get();

        return rank.getDiscount();
    }

    @Override
    public String updateUserRentDays(Long id, Long numOfDays) {
        User user = userRepository.findById(id).get();
        user.setRentDaysNumber(user.getRentDaysNumber()+numOfDays);
        userRepository.save(user);
        return "Successfully update number of rent days for user";
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

        //sending to msg broker
        //TODO u liniji iznad user se upise, ali ga je potrebno procitati opet iz baze da bismo
        //TODO dohvatili njegov id koji se salje ka brokeru
        PasswordChangedEmailDTO passwordChangedEmailDTO = new PasswordChangedEmailDTO();
        passwordChangedEmailDTO.setNotificationType("PASSEWORD_EMAIL");
        passwordChangedEmailDTO.setFirstName(user.getFirstName());
        passwordChangedEmailDTO.setLastName(user.getLastName());
        passwordChangedEmailDTO.setPassword(password);
        passwordChangedEmailDTO.setUserId(id);

        jmsTemplate.convertAndSend(destinationForPassword, messageHelper.createTextMessage(passwordChangedEmailDTO));

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
    public String updatePasseportNumber(Long id, String passeportNumber) {
        User user = userRepository.findById(id).get();
        user.setPassportNumber(passeportNumber);
        userRepository.save(user);
        return "Successfully updated passport number";
    }
}
