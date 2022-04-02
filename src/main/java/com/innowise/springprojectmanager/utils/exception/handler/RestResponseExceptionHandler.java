package com.innowise.springprojectmanager.utils.exception.handler;

import com.innowise.springprojectmanager.model.exception.ApiErrorDto;
import com.innowise.springprojectmanager.utils.exception.EntityNotFoundException;
import com.innowise.springprojectmanager.utils.exception.ValidationException;
import com.innowise.springprojectmanager.utils.literal.LogMessage;
import java.util.HashSet;
import java.util.Set;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Exception handler class.
 *
 * @author Edvard Krainiy on 02/04/2022
 */
@ControllerAdvice
@Log4j2
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {
  @Override
  protected @NonNull ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatus status,
      @NonNull WebRequest request) {
    Set<String> errors = new HashSet<>();
    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            error -> {
              String errorMessage = error.getDefaultMessage();
              errors.add(errorMessage);
            });

    log.error(
        String.format(LogMessage.METHOD_ARGUMENT_NOT_VALID_LOG, ex.getObjectName(), errors));

    ApiErrorDto error = new ApiErrorDto(HttpStatus.BAD_REQUEST.value(), errors);

    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {EntityNotFoundException.class})
  protected ResponseEntity<ApiErrorDto> handleEntityNotFoundException(EntityNotFoundException ex) {
    ApiErrorDto exceptionError = new ApiErrorDto(HttpStatus.NOT_FOUND.value(), ex.getMessage());

    return new ResponseEntity<>(exceptionError, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = {ValidationException.class})
  protected ResponseEntity<ApiErrorDto> handleValidationException(ValidationException ex) {
    ApiErrorDto exceptionError = new ApiErrorDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage());

    return new ResponseEntity<>(exceptionError, HttpStatus.BAD_REQUEST);
  }
}
