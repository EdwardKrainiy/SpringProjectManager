package com.innowise.springprojectmanager.utils.mapper.task;

import com.innowise.springprojectmanager.model.dto.task.TaskDto;
import com.innowise.springprojectmanager.model.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * DtoMapper interface, which contains methods to transform Task and TaskDto both ways.
 *
 * @author Edvard Krainiy on 04/04/2022
 */
@Mapper(componentModel = "spring")
public interface TaskDtoMapper {
  /**
   * TaskToTaskDto method. Converts Task object to TaskDto.
   *
   * @param task Task object we need to convert.
   * @return TaskDto Obtained TaskDto.
   */
  @Mapping(source = "task.project.id", target = "projectId")
  TaskDto toDto(Task task);

  /**
   * TaskDtoToTask method. Converts TaskDto to Task object.
   *
   * @param taskDto TaskDto we need to convert.
   * @return Task Obtained Task object.
   */
  Task toEntity(TaskDto taskDto);
}
