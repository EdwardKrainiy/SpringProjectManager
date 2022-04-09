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
  public static final String TITLE_IS_EMPTY_EXCEPTION_MESSAGE = "Title is empty!";
  public static final String DESCRIPTION_IS_EMPTY_EXCEPTION_MESSAGE = "Description is empty!";
  public static final String EMPTY_TASKS_EXCEPTION_MESSAGE = "Project tasks is empty!";
  public static final String MINIMAL_TASKS_SIZE_EXCEPTION_MESSAGE = "Must be at least 1 task in project!";
  public static final String TASK_EXPIRES_AT_IS_EMPTY_EXCEPTION_MESSAGE = "Expires at date is empty!";

  private ValidationExceptionMessage() {}
}
