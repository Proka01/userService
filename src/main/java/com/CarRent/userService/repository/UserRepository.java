package com.CarRent.userService.repository;

import ch.qos.logback.core.net.server.Client;
import com.CarRent.userService.model.Role;
import com.CarRent.userService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsernameAndPasswordAndRole_Id(String username, String password, Long role);


    @Query(value = "SELECT * FROM userservicedb.user u WHERE u.role_id = 1", nativeQuery = true)
    List<User> findAllClients();
    Optional<User> findUserByActivationCode(String code);
    Optional<User> findByUsername(String username);
}