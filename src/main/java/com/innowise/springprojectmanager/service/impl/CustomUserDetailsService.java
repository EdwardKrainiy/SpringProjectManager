package com.innowise.springprojectmanager.service.impl;

import com.innowise.springprojectmanager.repository.UserRepository;
import com.innowise.springprojectmanager.utils.exception.EntityNotFoundException;
import com.innowise.springprojectmanager.utils.literal.ExceptionMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * CustomUserDetailsService class for authentication.
 *
 * @author Edvard Krainiy on 02/04/2022
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  /**
   * loadUserByUsername method. Returns us userDetails of the user, found by username.
   *
   * @param username Username of the user, whose UserDetails we want to obtain.
   * @return UserDetails Returns UserDetails of found by username user.
   * @throws EntityNotFoundException If user wasn't found.
   */
  @Override
  public UserDetails loadUserByUsername(String username) {
    com.innowise.springprojectmanager.model.entity.User user =
        userRepository
            .findUserByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.USER_NOT_FOUND));
    return User.withUsername(user.getUsername())
        .password(user.getPassword())
        .authorities(user.getRole().name())
        .build();
  }
}
