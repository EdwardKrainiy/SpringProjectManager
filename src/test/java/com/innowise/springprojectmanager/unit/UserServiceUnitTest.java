package com.innowise.springprojectmanager.unit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.innowise.springprojectmanager.config.SecurityConfig;
import com.innowise.springprojectmanager.model.dto.user.UserSignUpDto;
import com.innowise.springprojectmanager.model.entity.User;
import com.innowise.springprojectmanager.model.enumeration.Role;
import com.innowise.springprojectmanager.repository.UserRepository;
import com.innowise.springprojectmanager.security.jwt.authentication.JwtAuthenticationByUserDetails;
import com.innowise.springprojectmanager.security.jwt.provider.TokenProvider;
import com.innowise.springprojectmanager.service.mail.impl.EmailServiceImpl;
import com.innowise.springprojectmanager.service.user.UserService;
import com.innowise.springprojectmanager.service.user.impl.CustomUserDetailsService;
import com.innowise.springprojectmanager.service.user.impl.UserServiceImpl;
import com.innowise.springprojectmanager.utils.JwtDecoder;
import com.innowise.springprojectmanager.utils.exception.EntityNotFoundException;
import com.innowise.springprojectmanager.utils.exception.IncorrectPasswordException;
import com.innowise.springprojectmanager.utils.exception.ValidationException;
import com.innowise.springprojectmanager.utils.literal.ExceptionMessage;
import com.innowise.springprojectmanager.utils.literal.PropertySourceClasspath;
import com.innowise.springprojectmanager.utils.mapper.user.UserDtoMapperImpl;
import com.innowise.springprojectmanager.utils.mapper.user.UserSignUpDtoMapper;
import com.innowise.springprojectmanager.utils.mapper.user.UserSignUpDtoMapperImpl;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(
    classes = {
        UserServiceImpl.class,
        CustomUserDetailsService.class,
        SecurityConfig.class,
        JavaMailSenderImpl.class,
        UserSignUpDtoMapperImpl.class,
        UserDtoMapperImpl.class,
        UserRepository.class,
        TokenProvider.class,
        JwtDecoder.class,
        JwtAuthenticationByUserDetails.class,
        EmailServiceImpl.class
    })
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = PropertySourceClasspath.JWT_PROPERTIES_CLASSPATH)
@TestPropertySource(locations = PropertySourceClasspath.MAIL_PROPERTIES_CLASSPATH)
@TestPropertySource(locations = PropertySourceClasspath.SECURITY_PROPERTIES_CLASSPATH)
@TestPropertySource(locations = PropertySourceClasspath.APPLICATION_PROPERTIES_CLASSPATH)
class UserServiceUnitTest {
  @MockBean private UserRepository userRepository;
  @SpyBean private CustomUserDetailsService customUserDetailsService;
  @SpyBean private UserSignUpDtoMapper userSignUpDtoMapper;
  @SpyBean private PasswordEncoder encoder;
  @SpyBean private UserService userService;

  @Test
  void givenUser_whenLoadUserByUsername_thenCheckCredentialsAndRoleOfObtainedUser() {
    User userToFound = new User("user", "user", null, Role.USER);
    when(userRepository.findUserByUsername("user")).thenReturn(Optional.of(userToFound));
    assertThat(customUserDetailsService.loadUserByUsername("user").getUsername())
        .isEqualTo(userToFound.getUsername());
    assertThat(customUserDetailsService.loadUserByUsername("user").getPassword())
        .isEqualTo(userToFound.getPassword());
    assertThat(customUserDetailsService.loadUserByUsername("user").getAuthorities().toString())
        .contains(userToFound.getRole().toString());
  }

  @Test
  void createdUserExists_whenCreateUser_thenValidationException() {
    UserSignUpDto anyUser = new UserSignUpDto("mail1@mail.ru");
    anyUser.setUsername("user");
    anyUser.setPassword("user");

    User anyUserEntity = userSignUpDtoMapper.toEntity(anyUser);
    anyUserEntity.setPassword(encoder.encode(anyUserEntity.getPassword()));
    anyUserEntity.setRole(Role.USER);
    anyUserEntity.setId(1L);

    when(userRepository.save(any(User.class))).thenReturn(anyUserEntity);
    when(userRepository.findUserByUsername(anyUserEntity.getUsername()))
        .thenReturn(Optional.of(anyUserEntity));
    when(userRepository.findUserByEmail(anyUserEntity.getEmail()))
        .thenReturn(Optional.of(anyUserEntity));

    Exception exception =
        assertThrows(ValidationException.class, () -> userService.createUser(anyUser));

    String expectedMessage = ExceptionMessage.USER_IS_ALREADY_EXISTS;
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void emptyUsers_andFindUserByUsername_thenEntityNotFoundException() {
    Exception exception =
        assertThrows(EntityNotFoundException.class, () -> userService.findUserByUsername("user"));

    String expectedMessage = ExceptionMessage.USER_NOT_FOUND;
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void givenUser_andFindUserByUsernameAndPassword_thenCheckAllValues() {
    User anyUser = new User("user", "user", "user@user.ru", Role.USER);

    when(userRepository.findUserByUsername("user")).thenReturn(Optional.of(anyUser));

    assertThat(userService.findUserByUsernameAndPassword("user", "user").getUsername())
        .isEqualTo(anyUser.getUsername());
    assertThat(userService.findUserByUsernameAndPassword("user", "user").getPassword())
        .isEqualTo(anyUser.getPassword());
  }

  @Test
  void
      givenUser_andFindUserByUsernameAndPassword_andPasswordIsWrong_thenIncorrectPasswordException() {
    User anyUser = new User("user", "user", "user@user.ru", Role.USER);

    when(userRepository.findUserByUsername("user")).thenReturn(Optional.of(anyUser));

    Exception exception =
        assertThrows(
            IncorrectPasswordException.class,
            () -> userService.findUserByUsernameAndPassword("user", "wrongPassword"));

    String expectedMessage = "Incorrect password for username = user!";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }
}
