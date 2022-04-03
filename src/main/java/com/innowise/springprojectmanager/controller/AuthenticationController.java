package com.innowise.springprojectmanager.controller;

import com.innowise.springprojectmanager.model.dto.user.UserSignInDto;
import com.innowise.springprojectmanager.service.UserService;
import com.innowise.springprojectmanager.utils.JsonEntitySerializer;
import com.innowise.springprojectmanager.utils.literal.LogMessage;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Authentication controller with sign-in, sign-up and email-confirmation endpoints.
 *
 * @author Edvard Krainiy on 02/04/2022
 */
@RestController
@Log4j2
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final UserService userService;

  private final JsonEntitySerializer jsonEntitySerializer;

  /**
   * Sign-in endpoint.
   *
   * @param userSignInDto User object we need to sign-in.
   * @return ResponseEntity Response, which contains message and HTTP code.
   */
  @ApiOperation(
      value = "Sign in.",
      notes = "Checks entered credentials and signs in user.",
      response = String.class)
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Successful sign in."),
        @ApiResponse(code = 400, message = "Bad request because of invalid credentials."),
        @ApiResponse(code = 404, message = "User with this credentials not found.")
      })
  @ResponseStatus(value = HttpStatus.OK)
  @PostMapping("/sign-in")
  public ResponseEntity<String> signIn(
      @RequestBody
          @Valid
          @ApiParam(name = "userSignInDto", value = "Dto of user, which we want to sign in.")
          UserSignInDto userSignInDto)
      throws AuthenticationException {

    if (log.isDebugEnabled()) {
      log.debug(
          String.format(
              LogMessage.DEBUG_REQUEST_BODY_LOG,
              jsonEntitySerializer.serializeObjectToJson(userSignInDto)));
    }
    return userService.authenticateUser(userSignInDto);
  }
}
