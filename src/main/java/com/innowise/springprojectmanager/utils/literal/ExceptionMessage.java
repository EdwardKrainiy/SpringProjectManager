package com.innowise.springprojectmanager.utils.literal;

/**
 * ExceptionMessage class. Contains all necessary messages of exceptions.
 *
 * @author Edvard Krainiy on 02/04/2022
 */
public class ExceptionMessage {
  public static final String USER_NOT_FOUND = "User not found!";
  public static final String USER_NOT_ACTIVATED_OR_DELETED = "This user not activated or deleted!";
  public static final String USER_IS_ALREADY_EXISTS = "This user is already exists!";
  public static final String USER_IS_ALREADY_ACTIVATED = "This user is already activated!";
  public static final String AUTHENTICATED_USER_NOT_FOUND = "Authenticated user not found!";
  public static final String USER_CANNOT_DELETE_HIMSELF = "User can't delete himself!";
  public static final String PROJECT_NOT_FOUND = "Project not found!";
  public static final String ID_OF_LOGGED_USER_NOT_EQUALS_ID_OF_PROJECT =  "Id of this project is not equals id of logged user!";

  private ExceptionMessage() {}
}
