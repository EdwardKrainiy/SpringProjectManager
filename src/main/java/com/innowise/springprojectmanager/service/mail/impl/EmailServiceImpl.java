package com.innowise.springprojectmanager.service.mail.impl;

import com.innowise.springprojectmanager.service.mail.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Implementation of EmailService interface. Provides us sending messages method.
 *
 * @author Edvard Krainiy on 03/04/2022
 */
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

  private final JavaMailSender emailSender;

  @Override
  public void sendEmail(String toAddress, String subject, String message) {
    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
    simpleMailMessage.setTo(toAddress);
    simpleMailMessage.setSubject(subject);
    simpleMailMessage.setText(message);
    emailSender.send(simpleMailMessage);
  }
}
