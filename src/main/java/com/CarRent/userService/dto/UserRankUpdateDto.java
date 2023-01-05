package com.CarRent.userService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRankUpdateDto {
    private Long id;
    private String name;
    private Long lowerBound;
    private Long upperBound;
    private Long discount;
}
