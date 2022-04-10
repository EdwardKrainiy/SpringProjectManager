package com.innowise.springprojectmanager.service.user;

import com.innowise.springprojectmanager.model.dto.user.UserDto;
import com.innowise.springprojectmanager.model.dto.user.UserSignInDto;
import com.innowise.springprojectmanager.model.dto.user.UserSignUpDto;
import com.innowise.springprojectmanager.model.entity.User;
import java.util.List;
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
   * isUserDeleted method. Checks, is User deleted.
   *
   * @param user User object, which we need to check.
   * @return Boolean isUserDeleted flag.
   */
  boolean isUserDeleted(User user);

  /**
   * createUser method. Saves our user on DB.
   *
   * @param userDto User transfer object, which we need to save. This one will be converted into
   *     User object, passed some checks and will be saved on DB.
   * @throws com.innowise.springprojectmanager.utils.exception.EntityNotFoundException If user wasn't found.
   * @throws com.innowise.springprojectmanager.utils.exception.EntityExistsException if user already exists.
   */
  void createUser(UserSignUpDto userDto);

  /**
   * authenticateUser method. Checks potential user and authenticate him. If this user is not
   * activated or not exists, Exception will be thrown.
   *
   * @param userSignInDto UserSignInDto object, which contains all necessary information to signing
   *     in.
   */
  ResponseEntity<String> authenticateUser(UserSignInDto userSignInDto);

  /**
   * activateUser method. Activates user, found by token.
   *
   * @param token Transferred token of the user we need to activate.
   */
  void activateUser(String token);

  /**
   * findAllUsers method. Finds all users from DB.
   *
   * @return ResponseEntity<List < UserDto>> ResponseEntity with HTTP code and list of all found
   *     users.
   */
  List<UserDto> findAllUsers();

  /**
   * findUserByUserId. Finds user by id.
   *
   * @param userId Id of user we need to find.
   * @return ResponseEntity<UserDto> ResponseEntity with HTTP code and found user entity.
   */
  UserDto findUserByUserId(Long userId);

  /**
   * findUserByUsername method. Finds user by username.
   *
   * @param username Username of the user we need to get from DB.
   * @return User Found by username User object. If user wasn't found, it will throw
   *     UserNotFoundException.
   */
  User findUserByUsername(String username);

  /**
   * findUserByUsernameAndPassword method. Finds user by username and password.
   *
   * @param username Username of the user we need to get from DB.
   * @param password Password of the user we need to get from DB.
   * @return User Found by username and password User object. If user wasn't found, it will throw
   *     UserNotFoundException.
   */
  User findUserByUsernameAndPassword(String username, String password);

  /**
   * deleteUserByUserId method. Deletes user by id.
   *
   * @param userId Id of user we need to delete.
   */
  void deleteUserByUserId(Long userId);
}
