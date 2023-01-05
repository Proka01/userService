package com.CarRent.userService.controller;

import com.CarRent.userService.dto.TokenRequestDto;
import com.CarRent.userService.dto.TokenResponseDto;
import com.CarRent.userService.dto.UserRankCreateDto;
import com.CarRent.userService.dto.UserRankUpdateDto;
import com.CarRent.userService.service.UserRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userRank")
public class UserRankController {

    private final UserRankService userRankService;

    public UserRankController(UserRankService userRankService) {
        this.userRankService = userRankService;
    }


    @PutMapping()
    public ResponseEntity<String> update(@RequestBody @Validated UserRankUpdateDto userRankUpdateDto) {
        return new ResponseEntity<>(userRankService.update(userRankUpdateDto), HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<String> create(@RequestBody @Validated UserRankCreateDto userRankCreateDto) {
        return new ResponseEntity<>(userRankService.create(userRankCreateDto), HttpStatus.CREATED);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return new ResponseEntity<>(userRankService.delete(id), HttpStatus.OK);
    }
}
