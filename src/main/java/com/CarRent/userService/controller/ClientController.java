package com.CarRent.userService.controller;

import com.CarRent.userService.dto.*;
import com.CarRent.userService.exception.NotFoundException;
import com.CarRent.userService.security.service.TokenService;
import com.CarRent.userService.service.ClientService;
import org.aspectj.weaver.ast.Not;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@RequestMapping("/client")
public class ClientController {

    private ClientService clientService;
    private TokenService tokenService;

    public ClientController(ClientService clientService, TokenService tokenService) {
        this.clientService = clientService;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ClientDto> saveUser(@RequestBody @Validated ClientRegisterDto clientRegisterDto) {
        return new ResponseEntity<>(clientService.insertClient(clientRegisterDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<TokenResponseDto> loginClient(@RequestBody @Validated TokenRequestDto tokenRequestDto) {
        try {
            return new ResponseEntity<>(clientService.login(tokenRequestDto), HttpStatus.OK);
        }
        catch (NotFoundException e){
            return new ResponseEntity<>(new TokenResponseDto("Credentials invalid"), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findById")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ClientDto> findById(@RequestHeader String authorization) {
        Long id = tokenService.parseId(authorization);
        System.out.println(authorization);
        return new ResponseEntity<>(clientService.findClientById(id), HttpStatus.OK);
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    //////////////////////// CLIENT INFO UPDATE CRUD //////////////////////////////////////
    @PutMapping("/updateUsername")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> updateUsername(@RequestBody @Validated UpdateAttribureDTO updateAttribureDTO, @RequestHeader String authorization) {
        Long id = tokenService.parseId(authorization);
        return new ResponseEntity<>(clientService.updateUsername(id, updateAttribureDTO.getValue()), HttpStatus.OK);
    }

    @PutMapping("/updatePassword")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> updatePassword(@RequestBody @Validated UpdateAttribureDTO updateAttribureDTO, @RequestHeader String authorization) {
        Long id = tokenService.parseId(authorization);
        return new ResponseEntity<>(clientService.updatePassword(id, updateAttribureDTO.getValue()), HttpStatus.OK);
    }

    @PutMapping("/updateEmail")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> updateEmail(@RequestBody @Validated UpdateAttribureDTO updateAttribureDTO, @RequestHeader String authorization) {
        Long id = tokenService.parseId(authorization);
        return new ResponseEntity<>(clientService.updateEmail(id, updateAttribureDTO.getValue()), HttpStatus.OK);
    }

    @PutMapping("/updateFirstName")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> updateFirstName(@RequestBody @Validated UpdateAttribureDTO updateAttribureDTO, @RequestHeader String authorization) {
        Long id = tokenService.parseId(authorization);
        return new ResponseEntity<>(clientService.updateFirstName(id, updateAttribureDTO.getValue()), HttpStatus.OK);
    }

    @PutMapping("/updateLastName")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> updateLastName(@RequestBody @Validated UpdateAttribureDTO updateAttribureDTO, @RequestHeader String authorization) {
        Long id = tokenService.parseId(authorization);
        return new ResponseEntity<>(clientService.updateLastName(id, updateAttribureDTO.getValue()), HttpStatus.OK);
    }

    @PutMapping("/updatePhoneNumber")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> updatePhoneNumber(@RequestBody @Validated UpdateAttribureDTO updateAttribureDTO, @RequestHeader String authorization) {
        Long id = tokenService.parseId(authorization);
        return new ResponseEntity<>(clientService.updatePhoneNumber(id, updateAttribureDTO.getValue()), HttpStatus.OK);
    }

    @PutMapping("/updatePassepotNumber")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> updatePasseportNumber(@RequestBody @Validated UpdateAttribureDTO updateAttribureDTO, @RequestHeader String authorization) {
        Long id = tokenService.parseId(authorization);
        return new ResponseEntity<>(clientService.updatePasseportNumber(id, updateAttribureDTO.getValue()), HttpStatus.OK);
    }

    @PutMapping("/updateBirthDate")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> updateBirthDate(@RequestBody @Validated UpdateAttribureDTO updateAttribureDTO, @RequestHeader String authorization) {
        Long id = tokenService.parseId(authorization);
        Date birthDate = Date.valueOf(updateAttribureDTO.getValue());
        return new ResponseEntity<>(clientService.updateBirthDate(id, birthDate), HttpStatus.OK);
    }

    /////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////

    @GetMapping("/verify/{code}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> verifyUser(@PathVariable("code") String code) {
       return new ResponseEntity<>(clientService.verifyUser(code), HttpStatus.OK);
    }

    //TODO dodaj kontrolere za ostale updateove

    @GetMapping("/rank/{id}")
    public ResponseEntity<Long> getDiscountForUser(@PathVariable("id") Long id) {
        return new ResponseEntity<>(clientService.getDiscountForUser(id), HttpStatus.OK);
    }

    @PostMapping("/increment")
    public ResponseEntity<String> incrementNumOfRentDays(@RequestBody @Validated ClientUpdateNumberOfRentDaysDto clientUpdateNumberOfRentDaysDto){
        return new ResponseEntity<>(clientService.updateUserRentDays(clientUpdateNumberOfRentDaysDto.getUserId(), clientUpdateNumberOfRentDaysDto.getNumOfDaysToAdd()), HttpStatus.OK);
    }

    @PostMapping("/decrement")
    public ResponseEntity<String> decrementNumOfRentDays(@RequestBody @Validated ClientUpdateNumberOfRentDaysDto clientUpdateNumberOfRentDaysDto){
        return new ResponseEntity<>(clientService.updateUserRentDays(clientUpdateNumberOfRentDaysDto.getUserId(), -clientUpdateNumberOfRentDaysDto.getNumOfDaysToAdd()), HttpStatus.OK);
    }
}
