package com.CarRent.userService.controller;

import com.CarRent.userService.dto.*;
import com.CarRent.userService.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }


    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ManagerDto> saveUser(@RequestBody @Validated ManagerRegisterDto managerRegisterDto) {
        return new ResponseEntity<>(managerService.insertManager(managerRegisterDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<TokenResponseDto> loginClient(@RequestBody @Validated TokenRequestDto tokenRequestDto) {
        return new ResponseEntity<>(managerService.login(tokenRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ManagerDto> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(managerService.findManagerById(id), HttpStatus.OK);
    }
}
