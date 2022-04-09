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
  public static final String USER_NOT_ACTIVATED_OR_DELETED_LOG = "User not activated or deleted. Username of requested user: %s";
  public static final String DEBUG_REQUEST_BODY_LOG = "Request body: {%s}";
  public static final String USER_IS_ALREADY_EXISTS_LOG = "User is already exists.";
  public static final String MANAGER_USER_NOT_EXISTS_LOG =
      "Manager user not exists, and confirmation message cannot be sent.";
  public static final String MESSAGE_SENT_LOG = "Message was successfully sent to email: %s";
  public static final String AUTHENTICATED_USER_NOT_FOUND_LOG = "Authenticated user not found.";
  public static final String USER_NOT_FOUND_LOG = "User not found. Id of requested user: %d";
  public static final String USER_DELETED_LOG = "User was deleted. Id of user: %d";
  public static final String USER_CANNOT_DELETE_HIMSELF_LOG = "User can't delete himself. Id of user: %d";
  public static final String PROJECT_NOT_FOUND_LOG = "Project not found. Id of project: %d";
  public static final String PROJECT_CREATED_LOG = "Project was created. Id of created Project: %d";
  public static final String ID_OF_LOGGED_USER_NOT_EQUALS_ID_OF_PROJECT_LOG =  "Id of logged user not equals id of project. Id of logged User: %d, Id of project user: %d";
  public static final String PROJECT_DELETED_LOG = "Project was deleted. Id of project: %d";
  public static final String PROJECT_UPDATED_LOG = "Account was updated. Id of account: %d";
  public static final String TASK_NOT_FOUND_LOG = "Task not found. Id of task: %d";
  public static final String TASK_DELETED_LOG = "Task was deleted. Id of task: %d";

  private LogMessage() {}
}
