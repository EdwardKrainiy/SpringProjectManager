package com.innowise.springprojectmanager.service.task;

import com.innowise.springprojectmanager.model.dto.task.TaskCreateDto;
import com.innowise.springprojectmanager.model.dto.task.TaskDto;
import com.innowise.springprojectmanager.model.dto.task.TaskUpdateDto;
import java.util.List;

/**
 * TaskService interface. Provides us different methods to work with Task objects on
 * Service layer.
 *
 * @author Edvard Krainiy on 09/04/2022
 */
public interface TaskService {

  /**
   * findTaskById method. Finds task by taskId and maps to DTO.
   *
   * @param taskId If of task we need to find.
   * @param projectId If of project, which task we need to find.
   * @return TaskDto Found taskDto object.
   */
  TaskDto findTaskById(Long taskId, Long projectId);

  /**
   * findAllTasks method. Finds all tasks, stored in DB.
   *
   * @param projectId If of project, which tasks we need to find.
   * @param sortBy SortBy param for sorting.
   * @param issuedAtMin IssuedAt min value to filter.
   * @param issuedAtMax IssuedAt min value to filter.
   * @param completed completed param to filter.
   *
   * @return List<TaskDto> List of all found taskDto objects.
   */
  List<TaskDto> findAndSortAllTasks(Long projectId, String sortBy, String issuedAtMin, String issuedAtMax, String completed);

  /**
   * createTask method. Creates Task from taskCreateDto.
   *
   * @param taskCreateDto taskCreateDto object to create new Task.
   * @param projectId If of project, which task we need to create.
   * @return TaskDto Obtained id of created TaskDto.
   */
  Long createTask(TaskCreateDto taskCreateDto, Long projectId);

  /**
   * updateTask method. Updates task by id and taskUpdateDto entity.
   *
   * @param taskUpdateDto Task transfer object, which we need to update. This one will be converted
   *                      into Task object, passed some checks and will be updated on DB.
   * @param taskId        Id of task we need to update.
   * @param projectId     Id of project, which task we need to update.
   */
  TaskDto updateTask(TaskUpdateDto taskUpdateDto, Long taskId, Long projectId);

  /**
   * deleteTaskById method. Deletes task by id.
   *
   * @param projectId Id of project, which we need to delete.
   * @param taskId Id of task we need to delete.
   */
  void deleteTaskById(Long taskId, Long projectId);

  /**
   * completeTask method. Completes task by setting Completed to TRUE.
   */
  Long completeTask(Long taskId, Long projectId);
}
