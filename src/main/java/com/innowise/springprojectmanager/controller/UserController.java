package com.innowise.springprojectmanager.controller;

import com.innowise.springprojectmanager.model.dto.user.UserDto;
import com.innowise.springprojectmanager.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController. Created to obtain all info about users.
 *
 * @author Edvard Krainiy on 04/04/2022
 */
@RestController
@Log4j2
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  /**
   * getAllUsers endpoint.
   *
   * @return List<User> All users in database.
   */
  @ApiOperation(
      value = "Obtain all users.",
      notes = "Returns all users, stored in DB",
      produces = "application/json",
      response = Iterable.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Successfully retrieved list of users."),
        @ApiResponse(
            code = 403,
            message = "Accessing the resource you were trying to reach is forbidden")
      })
  @GetMapping
  public ResponseEntity<List<UserDto>> getAllUsers() {
    return ResponseEntity.ok(userService.findAllUsers());
  }

  /**
   * getUserById endpoint.
   *
   * @param userId id of user we want to get.
   * @return ResponseEntity<UserDto> Response with HTTP code and UserDto of user that we
   *     found.
   */
  @ApiOperation(
      value = "Obtain user by id.",
      notes = "Returns user with id we noted.",
      produces = "application/json",
      response = UserDto.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Successfully retrieved user."),
        @ApiResponse(
            code = 403,
            message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "User with this id not found.")
      })
  @GetMapping("/{id}")
  public ResponseEntity<UserDto> getUserById(
      @PathVariable("id") @ApiParam(name = "id", value = "Id of user we need to obtain.")
          Long userId) {
    return ResponseEntity.ok(userService.findUserByUserId(userId));
  }

  /**
   * deleteUserById endpoint.
   *
   * @param userId id of User we want to delete.
   * @return ResponseEntity 204 HTTP code.
   */
  @ApiOperation(value = "Delete User.", notes = "Deletes user with id we noted.")
  @ApiResponses(
      value = {
        @ApiResponse(code = 204, message = "Successfully deleted user."),
        @ApiResponse(
            code = 403,
            message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "User not found.")
      })
  @Transactional
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUserById(
      @PathVariable("id") @ApiParam(name = "id", value = "Id of user we want to delete.")
          Long userId) {
    userService.deleteUserByUserId(userId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
