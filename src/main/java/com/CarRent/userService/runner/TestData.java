package com.CarRent.userService.runner;

import com.CarRent.userService.model.Role;
import com.CarRent.userService.model.User;
import com.CarRent.userService.repository.RoleRepository;
import com.CarRent.userService.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Profile({"default"})
@Component
public class TestData implements CommandLineRunner {

    private RoleRepository roleRepository;
    private UserRepository userRepository;

    public TestData(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
//        //Insert roles
//        Role roleUser = new Role("ROLE_USER", "User role");
//        Role roleManager = new Role("ROLE_MANAGER", "Manager role");
//        Role roleAdmin = new Role("ROLE_ADMIN", "Admin role");
//        roleRepository.save(roleUser);
//        roleRepository.save(roleManager);
//        roleRepository.save(roleAdmin);
//        //Insert admin
//        User admin = new User();
//        admin.setEmail("admin@gmail.com");
//        admin.setUsername("admin");
//        admin.setPassword("admin");
//        admin.setRole(roleAdmin);
//        userRepository.save(admin);
    }
}
