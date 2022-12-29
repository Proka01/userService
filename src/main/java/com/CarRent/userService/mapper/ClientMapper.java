package com.CarRent.userService.mapper;

import com.CarRent.userService.dto.ClientDto;
import com.CarRent.userService.dto.ClientRegisterDto;
import com.CarRent.userService.dto.ClientRestrictDto;
import com.CarRent.userService.model.Role;
import com.CarRent.userService.model.User;
import com.CarRent.userService.repository.RoleRepository;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class ClientMapper {

    private RoleRepository roleRepository;

    public ClientMapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public User clientRegisterDtoToUser(ClientRegisterDto clientRegisterDto){
        User user = new User();
        user.setUsername(clientRegisterDto.getUsername());
        user.setPassword(clientRegisterDto.getPassword());
        user.setEmail(clientRegisterDto.getEmail());
        user.setPhoneNumber(clientRegisterDto.getPhoneNumber());
        user.setBirthDate(clientRegisterDto.getBirthDate());
        user.setFirstName(clientRegisterDto.getFirstName());
        user.setLastName(clientRegisterDto.getLastName());
        user.setPassportNumber(clientRegisterDto.getPassportNumber());

        user.setRole(roleRepository.findRoleByName("ROLE_USER").get());
        user.setRestricted(false);
        user.setRentDaysNumber(0L);

        return user;
    }

    public ClientDto clientToClientDto(User user){
        ClientDto clientDto = new ClientDto();

        clientDto.setId(user.getId());
        clientDto.setEmail(user.getEmail());
        clientDto.setFirstName(user.getFirstName());
        clientDto.setLastName(user.getLastName());
        clientDto.setUsername(user.getUsername());
        clientDto.setPassword(user.getPassword());
        clientDto.setPhoneNumber(user.getPhoneNumber());
        clientDto.setBirthDate(user.getBirthDate());
        clientDto.setRole(user.getRole());
        clientDto.setPassportNumber(user.getPassportNumber());
        clientDto.setRentDaysNumber(user.getRentDaysNumber());
        clientDto.setRestricted(user.getRestricted());
        return clientDto;
    }

    public User clientRestrictDtoToUser(ClientRestrictDto clientRestrictDto){
        User user = new User();
        user.setRestricted(clientRestrictDto.isRestricted());
        user.setUsername(clientRestrictDto.getUsername());
        return user;
    }

}
