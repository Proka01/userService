package com.CarRent.userService.controller;

import com.CarRent.userService.dto.*;
import com.CarRent.userService.exception.NotFoundException;
import com.CarRent.userService.service.ClientService;
import org.aspectj.weaver.ast.Not;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
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

    @GetMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ClientDto> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(clientService.findClientById(id), HttpStatus.OK);
    }

    @GetMapping("/verify/{code}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> verifyUser(@PathVariable("code") String code) {
       return new ResponseEntity<>(clientService.verifyUser(code), HttpStatus.OK);
    }

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
