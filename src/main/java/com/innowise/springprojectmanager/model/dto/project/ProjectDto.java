package com.innowise.springprojectmanager.model.dto.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.innowise.springprojectmanager.model.dto.task.TaskDto;
import com.innowise.springprojectmanager.utils.literal.DtoJsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Project data-transfer object to manipulate with DB.
 *
 * @author Edvard Krainiy on 04/04/2022
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Project data-transfer object to manipulate with DB.")
public class ProjectDto {
  @Schema(description = "Unique Id field of Project.")
  @JsonProperty(DtoJsonProperty.ID)
  private Long id;

  @JsonProperty(DtoJsonProperty.USER_ID)
  @Schema(description = "User id field of Project.")
  private Long userId;

  @JsonProperty(DtoJsonProperty.TITLE)
  @Schema(description = "Title field of Project.")
  private String title;

  @JsonProperty(DtoJsonProperty.DESCRIPTION)
  @Schema(description = "Description field of Project.")
  private String description;

  @JsonProperty(DtoJsonProperty.ISSUED_AT)
  @Schema(description = "IssuedAt field of Project. Contain time and date of creation.")
  private Date issuedAt;

  @JsonProperty(DtoJsonProperty.TASKS)
  @Schema(description = "Tasks of Project.")
  private Set<TaskDto> tasks;
}

