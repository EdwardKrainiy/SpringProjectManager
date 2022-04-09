package com.innowise.springprojectmanager.model.dto.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Task data-transfer object to update existing Task.
 *
 * @author Edvard Krainiy on 09/04/2022
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "Task data-transfer object to update existing Task.")
public class TaskUpdateDto extends TaskCreateDto{
}
