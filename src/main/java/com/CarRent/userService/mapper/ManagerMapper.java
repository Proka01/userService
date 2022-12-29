package com.CarRent.userService.mapper;

import com.CarRent.userService.dto.ClientDto;
import com.CarRent.userService.dto.ClientRegisterDto;
import com.CarRent.userService.dto.ManagerDto;
import com.CarRent.userService.dto.ManagerRegisterDto;
import com.CarRent.userService.model.User;
import com.CarRent.userService.repository.RoleRepository;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
public class ManagerMapper {
    private final RoleRepository roleRepository;

    public ManagerMapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    public User managerRegisterDtoToUser(ManagerRegisterDto managerRegisterDto){
        User user = new User();
        user.setUsername(managerRegisterDto.getUsername());
        user.setPassword(managerRegisterDto.getPassword());
        user.setEmail(managerRegisterDto.getEmail());
        user.setPhoneNumber(managerRegisterDto.getPhoneNumber());
        user.setBirthDate(managerRegisterDto.getBirthDate());
        user.setFirstName(managerRegisterDto.getFirstName());
        user.setLastName(managerRegisterDto.getLastName());

        user.setCompanyName(managerRegisterDto.getCompanyName());

        user.setRole(roleRepository.findRoleByName("ROLE_MANAGER"));
        user.setHireDate(Date.valueOf(LocalDate.now()));

        return user;
    }


    public ManagerDto managerToManagerDto(User user){
        ManagerDto managerDto = new ManagerDto();

        managerDto.setId(user.getId());
        managerDto.setEmail(user.getEmail());
        managerDto.setFirstName(user.getFirstName());
        managerDto.setLastName(user.getLastName());
        managerDto.setUsername(user.getUsername());
        managerDto.setPassword(user.getPassword());
        managerDto.setPhoneNumber(user.getPhoneNumber());
        managerDto.setBirthDate(user.getBirthDate());
        managerDto.setRole(user.getRole());
        managerDto.setHireDate(user.getHireDate());
        managerDto.setCompanyName(user.getCompanyName());

        return managerDto;
    }
}
