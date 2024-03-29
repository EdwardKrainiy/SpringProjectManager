package com.innowise.springprojectmanager.security.jwt.authentication;

import com.innowise.springprojectmanager.model.dto.user.UserSignInDto;
import com.innowise.springprojectmanager.security.jwt.provider.TokenProvider;
import com.innowise.springprojectmanager.utils.literal.LogMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * JwtAuthentication class.
 *
 * @author Edvard Krainiy on 02/04/2022
 */
@Component
@RequiredArgsConstructor
@Log4j2
public class JwtAuthenticationByUserDetails {

  private final AuthenticationManager authenticationManager;

  private final TokenProvider jwtTokenUtil;

  /**
   * Authenticate method.
   *
   * @param userDto User object which we need to check and authenticate.
   * @return ResponseEntity Response, which contains message and HTTP code.
   */
  public ResponseEntity<String> authenticate(UserSignInDto userDto) {
    final Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    final String token = jwtTokenUtil.generateAuthToken(authentication);
    log.info(
        String.format(
            LogMessage.USER_AUTHENTICATED_LOG,
            userDto.getUsername(),
            userDto.getPassword(),
            token));
    return ResponseEntity.ok(token);
  }
}
