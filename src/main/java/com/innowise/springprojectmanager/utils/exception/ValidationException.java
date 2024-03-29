package com.innowise.springprojectmanager.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ValidationException class.
 *
 * @author Edvard Krainiy on 02/04/2022
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {
  public ValidationException(String message) {
    super(message);
  }
}
