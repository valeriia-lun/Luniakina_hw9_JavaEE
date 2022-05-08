package com.javaee.web.starter.service;

import com.javaee.web.starter.model.Book;
import com.javaee.web.starter.model.Role;
import com.javaee.web.starter.model.User;
import com.javaee.web.starter.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Slf4j
@Service
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  private final Role DEFAULT_ROLE = new Role(1L, "USER");

  @Autowired
  public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> foundUser = userRepository.findUserByUsernameIgnoreCase(username);
    if (foundUser.isEmpty()) {
      throw new UsernameNotFoundException("User is not found");
    }
    return foundUser.get();
  }

  @Transactional
  public Optional<User> findUserByUsername(String username) {
    return userRepository.findUserByUsernameIgnoreCase(username);
  }

  @Transactional
  public void signup(User user) {
    Optional<User> userByName = userRepository.findUserByUsernameIgnoreCase(user.getUsername());
    Optional<User> userByEmail = userRepository.findUserByEmailIgnoreCase(user.getEmail());

    if (nonNull(userByName) || nonNull(userByEmail)) {
      throw new IllegalArgumentException();
    }

    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    user.setRoles(Collections.singleton(DEFAULT_ROLE));

    userRepository.save(user);
  }

  @Transactional
  public void login(String username, String password) {
    Optional<User> foundUser = userRepository.findUserByUsernameIgnoreCase(username);
    if (foundUser.isEmpty() || !bCryptPasswordEncoder.matches(password, foundUser.get().getPassword())) {
      throw new IllegalArgumentException();
    }
  }

  @Transactional
  public void likeBook(Optional<User> user, Book book) {
    if (user.isPresent()) {
      user.get().getLikedBooks().add(book);
      book.getUsersLikes().add(user.get());
      userRepository.save(user.get());
    } else {
      throw new UsernameNotFoundException("User is not found");
    }
  }

  @Transactional
  public void dislikeBook(Optional<User> user, Book book) {
    if (user.isPresent()) {
      user.get().getLikedBooks().remove(book);
      book.getUsersLikes().remove(user.get());
      userRepository.save(user.get());
    } else {
      throw new UsernameNotFoundException("User is not found");
    }
  }

}
