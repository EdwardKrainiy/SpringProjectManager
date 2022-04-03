package com.innowise.springprojectmanager.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * UserExistsException class.
 *
 * @author Edvard Krainiy on 03/04/2022
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EntityExistsException extends RuntimeException {
  public EntityExistsException(String message) {
    super(message);
  }
}
