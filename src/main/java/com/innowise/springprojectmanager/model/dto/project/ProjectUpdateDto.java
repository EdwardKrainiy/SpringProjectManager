package com.innowise.springprojectmanager.model.dto.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.innowise.springprojectmanager.model.dto.task.TaskCreateDto;
import com.innowise.springprojectmanager.utils.literal.DtoJsonProperty;
import com.innowise.springprojectmanager.utils.literal.ValidationExceptionMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Project data-transfer object to update existing Project.
 *
 * @author Edvard Krainiy on 04/04/2022
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Project data-transfer object to update existing Project.")
public class ProjectUpdateDto {
  @JsonProperty(DtoJsonProperty.TASKS)
  @NotEmpty(message = ValidationExceptionMessage.EMPTY_TASKS_EXCEPTION_MESSAGE)
  @Size(min = 1, message = ValidationExceptionMessage.MINIMAL_TASKS_SIZE_EXCEPTION_MESSAGE)
  @Schema(description = "Set of tasks of Project.")
  private Set<@Valid TaskCreateDto> tasks;
}
