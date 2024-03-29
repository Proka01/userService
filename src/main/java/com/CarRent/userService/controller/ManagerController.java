package com.CarRent.userService.controller;

import com.CarRent.userService.dto.*;
import com.CarRent.userService.exception.NotFoundException;
import com.CarRent.userService.security.CheckSecurity;
import com.CarRent.userService.security.service.TokenService;
import com.CarRent.userService.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    private final ManagerService managerService;
    private final TokenService tokenService;

    public ManagerController(ManagerService managerService, TokenService tokenService) {
        this.managerService = managerService;
        this.tokenService = tokenService;
    }


    @PostMapping("/register")
    public ResponseEntity<ManagerDto> saveUser(@RequestBody @Validated ManagerRegisterDto managerRegisterDto) {
        return new ResponseEntity<>(managerService.insertManager(managerRegisterDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> loginClient(@RequestBody @Validated TokenRequestDto tokenRequestDto) {
        try {
            return new ResponseEntity<>(managerService.login(tokenRequestDto), HttpStatus.OK);
        }
        catch (NotFoundException e){
            return new ResponseEntity<>(new TokenResponseDto("Credentials invalid"), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findById")
    public ResponseEntity<ManagerDto> findById(@RequestHeader String authorization) {
        Long id = tokenService.parseId(authorization);
        return new ResponseEntity<>(managerService.findManagerById(id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<ManagerDto> getByIdNoToken(@PathVariable("id") Long id,@RequestHeader String authorization) {
        return new ResponseEntity<>(managerService.findManagerById(id), HttpStatus.OK);
    }


    ////////////////////////////////////////////////////////////////////////
    //////////// MANAGER UPDATE CRUD ///////////////////////////////////////

    @PutMapping("/updateUsername")
    public ResponseEntity<String> updateUsername(@RequestBody @Validated UpdateAttribureDTO updateAttribureDTO, @RequestHeader String authorization) {
        Long id = tokenService.parseId(authorization);
        return new ResponseEntity<>(managerService.updateUsername(id, updateAttribureDTO.getValue()), HttpStatus.OK);
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@RequestBody @Validated UpdateAttribureDTO updateAttribureDTO, @RequestHeader String authorization) {
        Long id = tokenService.parseId(authorization);
        return new ResponseEntity<>(managerService.updatePassword(id, updateAttribureDTO.getValue()), HttpStatus.OK);
    }

    @PutMapping("/updateEmail")
    public ResponseEntity<String> updateEmail(@RequestBody @Validated UpdateAttribureDTO updateAttribureDTO, @RequestHeader String authorization) {
        Long id = tokenService.parseId(authorization);
        return new ResponseEntity<>(managerService.updateEmail(id, updateAttribureDTO.getValue()), HttpStatus.OK);
    }

    @PutMapping("/updateFirstName")
    public ResponseEntity<String> updateFirstName(@RequestBody @Validated UpdateAttribureDTO updateAttribureDTO, @RequestHeader String authorization) {
        Long id = tokenService.parseId(authorization);
        return new ResponseEntity<>(managerService.updateFirstName(id, updateAttribureDTO.getValue()), HttpStatus.OK);
    }

    @PutMapping("/updateLastName")
    public ResponseEntity<String> updateLastName(@RequestBody @Validated UpdateAttribureDTO updateAttribureDTO, @RequestHeader String authorization) {
        Long id = tokenService.parseId(authorization);
        return new ResponseEntity<>(managerService.updateLastName(id, updateAttribureDTO.getValue()), HttpStatus.OK);
    }

    @PutMapping("/updatePhoneNumber")
    public ResponseEntity<String> updatePhoneNumber(@RequestBody @Validated UpdateAttribureDTO updateAttribureDTO, @RequestHeader String authorization) {
        Long id = tokenService.parseId(authorization);
        return new ResponseEntity<>(managerService.updatePhoneNumber(id, updateAttribureDTO.getValue()), HttpStatus.OK);
    }

    @PutMapping("/updateCompanyName")
    public ResponseEntity<String> updateCompanyName(@RequestBody @Validated UpdateAttribureDTO updateAttribureDTO, @RequestHeader String authorization) {
        Long id = tokenService.parseId(authorization);
        return new ResponseEntity<>(managerService.updateCompanyName(id, updateAttribureDTO.getValue()), HttpStatus.OK);
    }

    @PutMapping("/updateBirthDate")
    public ResponseEntity<String> updateBirthDate(@RequestBody @Validated UpdateAttribureDTO updateAttribureDTO, @RequestHeader String authorization) {
        Long id = tokenService.parseId(authorization);
        Date birthDate = Date.valueOf(updateAttribureDTO.getValue());
        return new ResponseEntity<>(managerService.updateBirthDate(id, birthDate), HttpStatus.OK);
    }
}
