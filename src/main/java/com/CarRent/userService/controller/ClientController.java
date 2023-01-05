package com.CarRent.userService.controller;

import com.CarRent.userService.dto.ClientDto;
import com.CarRent.userService.dto.ClientRegisterDto;
import com.CarRent.userService.dto.TokenRequestDto;
import com.CarRent.userService.dto.TokenResponseDto;
import com.CarRent.userService.service.ClientService;
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
    public ResponseEntity<ClientDto> saveUser(@RequestBody @Validated ClientRegisterDto clientRegisterDto) {
        return new ResponseEntity<>(clientService.insertClient(clientRegisterDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> loginClient(@RequestBody @Validated TokenRequestDto tokenRequestDto) {
        return new ResponseEntity<>(clientService.login(tokenRequestDto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(clientService.findClientById(id), HttpStatus.OK);
    }

    @GetMapping("/verify/{code}")
    public ResponseEntity<String> verifyUser(@PathVariable("code") String code) {
       return new ResponseEntity<>(clientService.verifyUser(code), HttpStatus.OK);
    }

    @GetMapping("/rank/{id}")
    public ResponseEntity<Long> verifyUser(@PathVariable("id") Long id) {
        return new ResponseEntity<>(clientService.getDiscountForUser(id), HttpStatus.OK);
    }
}
