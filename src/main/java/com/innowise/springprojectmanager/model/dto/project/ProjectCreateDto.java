package com.innowise.springprojectmanager.model.dto.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.innowise.springprojectmanager.utils.literal.DtoJsonProperty;
import com.innowise.springprojectmanager.utils.literal.ValidationExceptionMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Project data-transfer object to create new Project.
 *
 * @author Edvard Krainiy on 04/04/2022
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Project data-transfer object to create new Project.")
public class ProjectCreateDto extends ProjectUpdateDto {

  @NotBlank(message = ValidationExceptionMessage.TITLE_IS_EMPTY_EXCEPTION_MESSAGE)
  @JsonProperty(DtoJsonProperty.TITLE)
  @Schema(description = "Title field of Project.")
  private String title;

  @NotBlank(message = ValidationExceptionMessage.DESCRIPTION_IS_EMPTY_EXCEPTION_MESSAGE)
  @JsonProperty(DtoJsonProperty.DESCRIPTION)
  @Schema(description = "Description field of Project.")
  private String description;
}
