package com.CarRent.userService.controller;

import com.CarRent.userService.dto.ClientDto;
import com.CarRent.userService.dto.ClientRestrictDto;
import com.CarRent.userService.dto.ManagerDto;
import com.CarRent.userService.dto.ManagerRegisterDto;
import com.CarRent.userService.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/restrict")
    public ResponseEntity<ClientDto> saveUser(@RequestBody @Validated ClientRestrictDto clientRestrictDto) {
        return new ResponseEntity<>(adminService.updateClientRestricted(clientRestrictDto), HttpStatus.CREATED);
    }
}
