package com.innowise.springprojectmanager.model.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.innowise.springprojectmanager.utils.literal.DtoJsonProperty;
import com.innowise.springprojectmanager.utils.literal.RegexPattern;
import com.innowise.springprojectmanager.utils.literal.ValidationExceptionMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User data-transfer object to authenticate existing User.
 *
 * @author Edvard Krainiy on 02/04/2022
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User data-transfer object to sign in application.")
public class UserSignInDto {
  @JsonProperty(DtoJsonProperty.USERNAME)
  @Pattern(
      regexp = RegexPattern.VALID_USERNAME_ADDRESS_REGEX,
      message = ValidationExceptionMessage.USERNAME_IS_NOT_VALID_EXCEPTION_MESSAGE)
  @NotBlank(message = ValidationExceptionMessage.USERNAME_IS_EMPTY_EXCEPTION_MESSAGE)
  @Schema(description = "Unique Username field of User.")
  private String username;

  @JsonProperty(DtoJsonProperty.PASSWORD)
  @NotBlank(message = ValidationExceptionMessage.PASSWORD_IS_EMPTY_MESSAGE_TEXT)
  @Size(min = 5, max = 20, message = ValidationExceptionMessage.INCORRECT_PASSWORD_LENGTH_MESSAGE_TEXT)
  @Schema(description = "Password field of User.")
  private String password;
}
