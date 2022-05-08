package com.javaee.web.starter.repository;

import com.javaee.web.starter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findUserByEmailIgnoreCase(String email);

  Optional<User> findUserByUsernameIgnoreCase(String username);

}
