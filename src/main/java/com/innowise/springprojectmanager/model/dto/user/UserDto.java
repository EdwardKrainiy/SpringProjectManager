package com.innowise.springprojectmanager.model.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.innowise.springprojectmanager.utils.literal.DtoJsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User data-transfer object to manipulate with DB.
 *
 * @author Edvard Krainiy on 04/04/2022
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User data-transfer object to manipulate with DB.")
public class UserDto {
  @Schema(description = "Unique Id field of User.")
  @JsonProperty(DtoJsonProperty.ID)
  private Long id;

  @JsonProperty(DtoJsonProperty.USERNAME)
  @Schema(description = "Unique username field of User.")
  private String username;

  @JsonProperty(DtoJsonProperty.PASSWORD)
  @Schema(description = "Password field of User.")
  private String password;

  @JsonProperty(DtoJsonProperty.EMAIL)
  @Schema(description = "Email field of User.")
  private String email;

  @JsonProperty(DtoJsonProperty.ROLE)
  @Schema(description = "Role field of User.")
  private String role;

  @JsonProperty(DtoJsonProperty.ACTIVATED)
  @Schema(description = "Activated flag of User.")
  private String activated;

  @JsonProperty(DtoJsonProperty.DELETED)
  @Schema(description = "Deleted flag of User.")
  private String deleted;
}
