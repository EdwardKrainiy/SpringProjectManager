package com.innowise.springprojectmanager.utils.literal;

/**
 * PropertySourceClasspath class. Contains all necessary classpaths of .property files.
 *
 * @author Edvard Krainiy on 02/04/2022
 */
public class PropertySourceClasspath {
  public static final String SECURITY_PROPERTIES_CLASSPATH =
      "classpath:properties/security.properties";
  public static final String MAIL_PROPERTIES_CLASSPATH = "classpath:properties/mail.properties";
  public static final String JWT_PROPERTIES_CLASSPATH = "classpath:properties/jwt.properties";;

  private PropertySourceClasspath() {}
}
