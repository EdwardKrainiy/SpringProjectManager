package com.innowise.springprojectmanager.service.user.impl;

import com.innowise.springprojectmanager.model.dto.user.UserSignInDto;
import com.innowise.springprojectmanager.model.dto.user.UserSignUpDto;
import com.innowise.springprojectmanager.model.entity.User;
import com.innowise.springprojectmanager.model.enumeration.Role;
import com.innowise.springprojectmanager.repository.UserRepository;
import com.innowise.springprojectmanager.security.jwt.authentication.JwtAuthenticationByUserDetails;
import com.innowise.springprojectmanager.security.jwt.provider.TokenProvider;
import com.innowise.springprojectmanager.service.UserService;
import com.innowise.springprojectmanager.service.mail.EmailService;
import com.innowise.springprojectmanager.utils.JwtDecoder;
import com.innowise.springprojectmanager.utils.exception.EntityNotFoundException;
import com.innowise.springprojectmanager.utils.exception.ValidationException;
import com.innowise.springprojectmanager.utils.literal.ExceptionMessage;
import com.innowise.springprojectmanager.utils.literal.LogMessage;
import com.innowise.springprojectmanager.utils.literal.PropertySourceClasspath;
import com.innowise.springprojectmanager.utils.mapper.UserSignUpDtoMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of UserService interface. Provides us different methods of Service layer to work
 * with Repository layer of User objects.
 *
 * @author Edvard Krainiy on 02/04/2022
 */
@Service
@Log4j2
@PropertySource(PropertySourceClasspath.MAIL_PROPERTIES_CLASSPATH)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final JwtAuthenticationByUserDetails jwtAuthenticationByUserDetails;
  private final UserSignUpDtoMapper userSignUpDtoMapper;
  private final TokenProvider tokenProvider;
  private final PasswordEncoder encoder;
  private final JwtDecoder jwtDecoder;
  private final EmailService emailService;

  @Value("${mail.confirmation.message}")
  private String confirmMessage;

  @Value("${mail.user.confirmation.title}")
  private String userConfirmationMessageTitleText;

  @Value("${mail.user.successful.confirmation.title}")
  private String successfulConfirmationTitle;

  @Value("${mail.user.successful.confirmation.message}")
  private String successfulConfirmationMessage;

  @Override
  public boolean isUserActivated(User user) {
    return user.getConfirmationToken() == null && user.isActivated();
  }

  @Override
  public ResponseEntity<String> authenticateUser(UserSignInDto userSignInDto) {
    User userToSignIn;
    Optional<User> userToSignInOptional =
        userRepository.findUserByUsername(userSignInDto.getUsername());
    if (!userToSignInOptional.isPresent()) {
      log.error(
          String.format(LogMessage.USER_WITH_USERNAME_NOT_FOUND_LOG, userSignInDto.getUsername()));
      throw new EntityNotFoundException(ExceptionMessage.USER_NOT_FOUND);
    } else {
      userToSignIn = userToSignInOptional.get();
    }
    if (isUserActivated(userToSignIn)) {
      return jwtAuthenticationByUserDetails.authenticate(userSignInDto);
    } else {
      log.error(String.format(LogMessage.USER_NOT_ACTIVATED_LOG, userToSignIn.getUsername()));
      throw new ValidationException(ExceptionMessage.USER_NOT_ACTIVATED);
    }
  }

  @Override
  public void createUser(UserSignUpDto userDto) {

    User mappedUser = userSignUpDtoMapper.toEntity(userDto);

    if (userRepository.findUserByUsername(mappedUser.getUsername()).isPresent()
        || userRepository.findUserByEmail(mappedUser.getEmail()).isPresent()) {
      log.error(LogMessage.USER_IS_ALREADY_EXISTS_LOG);
      throw new ValidationException(ExceptionMessage.USER_IS_ALREADY_EXISTS);
    }

    User createdUser =
        new User(
            mappedUser.getUsername(),
            encoder.encode(mappedUser.getPassword()),
            mappedUser.getEmail(),
            Role.USER);

    Long createdUserId = userRepository.save(createdUser).getId();

    String confirmationToken = tokenProvider.generateConfirmToken(createdUserId);

    createdUser.setConfirmationToken(confirmationToken);

    userRepository.save(createdUser);

    Optional<User> managerUserOptional = userRepository.findUserByRole(Role.ADMIN);
    if (!managerUserOptional.isPresent()) {
      log.error(LogMessage.MANAGER_USER_NOT_EXISTS_LOG);
      throw new EntityNotFoundException(ExceptionMessage.USER_NOT_FOUND);
    } else {
      emailService.sendEmail(
          managerUserOptional.get().getEmail(),
          String.format(userConfirmationMessageTitleText, mappedUser.getUsername()),
          String.format(("%s%s"), confirmMessage, confirmationToken));

      log.info(String.format(LogMessage.MESSAGE_SENT_LOG, managerUserOptional.get().getEmail()));
    }
  }

  @Transactional
  @Override
  public void activateUser(String token) {
    Long userId = jwtDecoder.getIdFromConfirmToken(token);

    User activatedUser = userRepository.getById(userId);

    if (activatedUser.getConfirmationToken() == null) {
      throw new ValidationException(ExceptionMessage.USER_IS_ALREADY_ACTIVATED);
    }

    activatedUser.setConfirmationToken(null);
    activatedUser.setActivated(true);

    userRepository.save(activatedUser);

    emailService.sendEmail(
        activatedUser.getEmail(), successfulConfirmationTitle, successfulConfirmationMessage);
    log.info(String.format(LogMessage.MESSAGE_SENT_LOG, activatedUser.getEmail()));
  }
}