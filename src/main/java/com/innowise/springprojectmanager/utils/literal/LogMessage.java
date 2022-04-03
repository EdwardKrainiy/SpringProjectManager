package com.innowise.springprojectmanager.utils.literal;

/**
 * LogMessage class. Contains all necessary messages of logs.
 *
 * @author Edvard Krainiy on 02/04/2022
 */
public class LogMessage {
  public static final String USER_AUTHENTICATED_LOG =
      "User was authenticated. Username: %s Password: %s Token: %s";
  public static final String METHOD_ARGUMENT_NOT_VALID_LOG =
      "MethodArgumentNotValidException was caught and successfully handled. Name of object: %s. List of all validation errors: %s";
  public static final String USER_WITH_USERNAME_NOT_FOUND_LOG = "User not found. Username of requested user: %s";
  public static final String USER_NOT_ACTIVATED_LOG = "User not activated. Username of requested user: %s";
  public static final String DEBUG_REQUEST_BODY_LOG = "Request body: {%s}";

  private LogMessage() {}
}
