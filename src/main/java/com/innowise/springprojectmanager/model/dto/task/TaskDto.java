package com.innowise.springprojectmanager.model.dto.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.innowise.springprojectmanager.utils.literal.DtoJsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Task data-transfer object to manipulate with DB.
 *
 * @author Edvard Krainiy on 04/04/2022
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Task data-transfer object to manipulate with DB.")
public class TaskDto {
  @Schema(description = "Unique Id field of Task.")
  @JsonProperty(DtoJsonProperty.ID)
  private Long id;

  @JsonProperty(DtoJsonProperty.PROJECT_ID)
  @Schema(description = "User id field of Task.")
  private Long projectId;

  @JsonProperty(DtoJsonProperty.TITLE)
  @Schema(description = "Title field of Task.")
  private String title;

  @JsonProperty(DtoJsonProperty.DESCRIPTION)
  @Schema(description = "Description field of Task.")
  private String description;

  @JsonProperty(DtoJsonProperty.ISSUED_AT)
  @Schema(description = "IssuedAt field of Task. Contain time and date of creation.")
  private Date issuedAt;

  @JsonProperty(DtoJsonProperty.EXPIRES_AT)
  @Schema(description = "ExpiresAt field of Task. Contain time and date of expiration.")
  private Date expiresAt;

  @JsonProperty(DtoJsonProperty.DELETED)
  @Schema(description = "Deleted flag of Task.")
  private String deleted;

  @JsonProperty(DtoJsonProperty.COMPLETED)
  @Schema(description = "Completed flag of Task.")
  private String completed;
}
