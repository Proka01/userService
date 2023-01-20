package com.CarRent.userService.controller;

import com.CarRent.userService.dto.*;
import com.CarRent.userService.security.CheckSecurity;
import com.CarRent.userService.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/restrict")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<ClientDto> saveUser(@RequestBody @Validated ClientRestrictDto clientRestrictDto, @RequestHeader("Authorization") String authorization) {
        return new ResponseEntity<>(adminService.updateClientRestricted(clientRestrictDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> loginClient(@RequestBody @Validated TokenRequestDto tokenRequestDto) {
        return new ResponseEntity<>(adminService.login(tokenRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("/getAllClients")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<List<ClientDto>> getAllNotifications(@RequestHeader String authorization) {
        return new ResponseEntity<>(adminService.getAllClients(), HttpStatus.OK);
    }
}
