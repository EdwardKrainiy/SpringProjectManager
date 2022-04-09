package com.innowise.springprojectmanager.model.dto.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.innowise.springprojectmanager.utils.literal.DtoJsonProperty;
import com.innowise.springprojectmanager.utils.literal.ValidationExceptionMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Task data-transfer object to create new Task.
 *
 * @author Edvard Krainiy on 04/04/2022
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Task data-transfer object to create new Task.")
public class TaskCreateDto {
  @NotBlank(message = ValidationExceptionMessage.TITLE_IS_EMPTY_EXCEPTION_MESSAGE)
  @JsonProperty(DtoJsonProperty.TITLE)
  @Schema(description = "Title field of Task.")
  private String title;

  @NotBlank(message = ValidationExceptionMessage.DESCRIPTION_IS_EMPTY_EXCEPTION_MESSAGE)
  @JsonProperty(DtoJsonProperty.DESCRIPTION)
  @Schema(description = "Description field of Task.")
  private String description;

  @NotBlank(message = ValidationExceptionMessage.TASK_EXPIRES_AT_IS_EMPTY_EXCEPTION_MESSAGE)
  @JsonProperty(DtoJsonProperty.EXPIRES_AT)
  @Schema(description = "ExpiresAt field of Task.")
  private String expiresAt;
}
