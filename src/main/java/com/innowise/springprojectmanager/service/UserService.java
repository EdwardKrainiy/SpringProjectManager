package com.innowise.springprojectmanager.service;

import com.innowise.springprojectmanager.model.dto.user.UserSignInDto;
import com.innowise.springprojectmanager.model.entity.User;
import org.springframework.http.ResponseEntity;

/**
 * UserService interface. Provides us different methods to work with User objects on Service layer.
 *
 * @author Edvard Krainiy on 02/04/2022
 */
public interface UserService {

  /**
   * isUserActivated method. Checks, is User activated.
   *
   * @param user User object, which we need to check.
   * @return Boolean isUserActivated flag.
   */
  boolean isUserActivated(User user);

  /**
   * authenticateUser method. Checks potential user and authenticate him. If this user is not
   * activated or not exists, Exception will be thrown.
   *
   * @param userSignInDto UserSignInDto object, which contains all necessary information to signing
   *     in.
   */
  ResponseEntity<String> authenticateUser(UserSignInDto userSignInDto);
}
