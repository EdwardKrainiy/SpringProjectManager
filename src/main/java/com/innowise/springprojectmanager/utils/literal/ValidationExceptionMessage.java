package com.innowise.springprojectmanager.utils.literal;

/**
 * ValidationExceptionMessage class. Contains all necessary Validation Exception messages.
 *
 * @author Edvard Krainiy on 02/04/2022
 */
public class ValidationExceptionMessage {
  public static final String USERNAME_IS_NOT_VALID_EXCEPTION_MESSAGE = "Username is not valid!";
  public static final String USERNAME_IS_EMPTY_EXCEPTION_MESSAGE = "Username is empty!";
  public static final String PASSWORD_IS_EMPTY_MESSAGE_TEXT = "Password is empty!";
  public static final String INCORRECT_PASSWORD_LENGTH_MESSAGE_TEXT =
      "Incorrect password length! It must be from 5 to 20.";
  public static final String EMAIL_IS_NOT_VALID_EXCEPTION_MESSAGE_TEXT = "Email is not valid!";
  public static final String EMAIL_IS_EMPTY_MESSAGE_TEXT = "Email is empty!";

  private ValidationExceptionMessage() {}
}
