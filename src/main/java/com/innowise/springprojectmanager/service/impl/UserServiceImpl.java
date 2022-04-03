package com.innowise.springprojectmanager.service.impl;

import com.innowise.springprojectmanager.model.dto.user.UserSignInDto;
import com.innowise.springprojectmanager.model.entity.User;
import com.innowise.springprojectmanager.repository.UserRepository;
import com.innowise.springprojectmanager.security.jwt.authentication.JwtAuthenticationByUserDetails;
import com.innowise.springprojectmanager.service.UserService;
import com.innowise.springprojectmanager.utils.exception.EntityNotFoundException;
import com.innowise.springprojectmanager.utils.exception.ValidationException;
import com.innowise.springprojectmanager.utils.literal.ExceptionMessage;
import com.innowise.springprojectmanager.utils.literal.LogMessage;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Implementation of UserService interface. Provides us different methods of Service layer to work
 * with Repository layer of User objects.
 *
 * @author Edvard Krainiy on 02/04/2022
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final JwtAuthenticationByUserDetails jwtAuthenticationByUserDetails;

  @Override
  public boolean isUserActivated(User user) {
    return user.getConfirmationToken() == null && user.isActivated();
  }

  @Override
  public ResponseEntity<String> authenticateUser(UserSignInDto userSignInDto) {
    User userToSignIn;
    Optional<User> userToSignInOptional =
        userRepository.findUserByUsername(userSignInDto.getUsername());
    if (!userToSignInOptional.isPresent()) {
      log.error(
          String.format(LogMessage.USER_WITH_USERNAME_NOT_FOUND_LOG, userSignInDto.getUsername()));
      throw new EntityNotFoundException(ExceptionMessage.USER_NOT_FOUND);
    } else {
      userToSignIn = userToSignInOptional.get();
    }
    if (isUserActivated(userToSignIn)) {
      return jwtAuthenticationByUserDetails.authenticate(userSignInDto);
    } else {
      log.error(String.format(LogMessage.USER_NOT_ACTIVATED_LOG, userToSignIn.getUsername()));
      throw new ValidationException(ExceptionMessage.USER_NOT_ACTIVATED);
    }
  }
}
