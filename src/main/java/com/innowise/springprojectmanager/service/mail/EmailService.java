package com.innowise.springprojectmanager.service.mail;

/**
 * EmailSender interface. Provides us different methods to work with MailSender.
 *
 * @author Edvard Krainiy on 03/04/2022
 */
public interface EmailService {

  /**
   * sendEmail method.
   *
   * @param toAddress Address we want to send the message.
   * @param subject Message subject.
   * @param message Text of the message.
   */
  void sendEmail(String toAddress, String subject, String message);
}
