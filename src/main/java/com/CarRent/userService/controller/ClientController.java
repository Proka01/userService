package com.CarRent.userService.controller;

import com.CarRent.userService.dto.ClientDto;
import com.CarRent.userService.dto.ClientRegisterDto;
import com.CarRent.userService.dto.TokenRequestDto;
import com.CarRent.userService.dto.TokenResponseDto;
import com.CarRent.userService.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/register")
    public ResponseEntity<ClientDto> saveUser(@RequestBody @Validated ClientRegisterDto clientRegisterDto) {
        return new ResponseEntity<>(clientService.insertClient(clientRegisterDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> loginClient(@RequestBody @Validated TokenRequestDto tokenRequestDto) {
        return new ResponseEntity<>(clientService.login(tokenRequestDto), HttpStatus.CREATED);
    }
}
