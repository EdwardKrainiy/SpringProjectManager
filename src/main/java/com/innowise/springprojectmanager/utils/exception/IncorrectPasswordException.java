package com.innowise.springprojectmanager.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * IncorrectPasswordException class.
 *
 * @author Edvard Krainiy on 10/04/2022
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IncorrectPasswordException extends RuntimeException {
  public IncorrectPasswordException(String username) {
    super(String.format("Incorrect password for username = %s!", username));
  }
}
