package com.innowise.springprojectmanager.utils;

import com.innowise.springprojectmanager.model.entity.User;
import com.innowise.springprojectmanager.repository.UserRepository;
import com.innowise.springprojectmanager.utils.exception.EntityNotFoundException;
import com.innowise.springprojectmanager.utils.literal.ExceptionMessage;
import com.innowise.springprojectmanager.utils.literal.LogMessage;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * JwtDecoder class, which contains methods to decode the JWT and obtain claims we need.
 *
 * @author Edvard Krainiy on 03/04/2022
 */
@Component
@RequiredArgsConstructor
@Log4j2
public class JwtDecoder {
  private final UserRepository userRepository;

  @Value("${jwt.confirmation.key}")
  private String confirmationKey;

  /**
   * getIdFromConfirmToken method. Gets id from transferred token.
   *
   * @param token Transferred token of the user, whose id we need to obtain.
   * @return Long Obtained Id of User from token.
   * @throws ExpiredJwtException If token was expired.
   */
  public Long getIdFromConfirmToken(String token) throws ExpiredJwtException {

    Claims confirmationClaims =
        Jwts.parser().setSigningKey(confirmationKey).parseClaimsJws(token).getBody();

    Optional<Long> userId = Optional.of(Long.parseLong(confirmationClaims.getSubject()));

    return userId.orElseThrow(() -> new EntityNotFoundException("Id not found"));
  }

  /**
   * getLoggedUser method. Returns logged User from JWT.
   *
   * @return User logged user. EntityNotFound exception will be thrown, if user not logged.
   */
  public User getLoggedUser() {
    Optional<User> authenticatedUserOptional =
        userRepository.findUserByUsername(getUsernameOfLoggedUser());

    if (authenticatedUserOptional.isPresent()) {
      return authenticatedUserOptional.get();
    } else {
      log.error(LogMessage.AUTHENTICATED_USER_NOT_FOUND_LOG);
      throw new EntityNotFoundException(ExceptionMessage.AUTHENTICATED_USER_NOT_FOUND);
    }
  }

  /**
   * getUsernameOfLoggedUser method. Returns username of logged user.
   *
   * @return String username of logged user.
   */
  public String getUsernameOfLoggedUser() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserDetails) {
      return ((UserDetails) principal).getUsername();
    } else {
      return principal.toString();
    }
  }
}
