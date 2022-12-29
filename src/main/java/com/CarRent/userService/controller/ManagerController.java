package com.CarRent.userService.controller;

import com.CarRent.userService.dto.ClientDto;
import com.CarRent.userService.dto.ClientRegisterDto;
import com.CarRent.userService.dto.ManagerDto;
import com.CarRent.userService.dto.ManagerRegisterDto;
import com.CarRent.userService.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }


    @PostMapping("/register")
    public ResponseEntity<ManagerDto> saveUser(@RequestBody @Validated ManagerRegisterDto managerRegisterDto) {
        return new ResponseEntity<>(managerService.insertManager(managerRegisterDto), HttpStatus.CREATED);
    }
}
