package com.innowise.springprojectmanager.controller;

import com.innowise.springprojectmanager.model.dto.task.TaskCreateDto;
import com.innowise.springprojectmanager.model.dto.task.TaskDto;
import com.innowise.springprojectmanager.model.dto.task.TaskUpdateDto;
import com.innowise.springprojectmanager.service.task.TaskService;
import com.innowise.springprojectmanager.utils.JsonEntitySerializer;
import com.innowise.springprojectmanager.utils.literal.LogMessage;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * TaskController. Created to obtain all info about tasks.
 *
 * @author Edvard Krainiy on 09/04/2022
 */
@RestController
@Log4j2
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
  private final TaskService taskService;
  private final JsonEntitySerializer jsonEntitySerializer;

  /**
   * getAllTasks endpoint.
   *
   * @param projectId id of project, which tasks we want to get.
   * @return List<Task> All tasks of project in database.
   */
  @ApiOperation(
      value = "Obtain all tasks.",
      notes = "Returns all tasks, stored in DB",
      produces = "application/json",
      response = Iterable.class)
  @ApiResponses(
      value = {
          @ApiResponse(code = 200, message = "Successfully retrieved list of tasks."),
          @ApiResponse(
              code = 403,
              message = "Accessing the resource you were trying to reach is forbidden")
      })
  @GetMapping("/{projectId}")
  public ResponseEntity<List<TaskDto>> getAllTasks(@PathVariable("projectId") @ApiParam(name = "projectId", value = "Id of project, which tasks we need to obtain.") Long projectId) {
    return ResponseEntity.ok(taskService.findAllTasks(projectId));
  }

  /**
   * getTaskById endpoint.
   *
   * @param taskId id of Task we want to get.
   * @param projectId id of project, which tasks we want to get.

   * @return ResponseEntity<TaskDto> Response with HTTP code and TaskDto of Task that we
   *     found.
   */
  @ApiOperation(
      value = "Obtain task by id.",
      notes = "Returns task with id we noted.",
      produces = "application/json",
      response = TaskDto.class)
  @ApiResponses(
      value = {
          @ApiResponse(code = 200, message = "Successfully retrieved task."),
          @ApiResponse(
              code = 403,
              message = "Accessing the resource you were trying to reach is forbidden"),
          @ApiResponse(code = 404, message = "Task with this id not found.")
      })
  @GetMapping("/{projectId}/{taskId}")
  public ResponseEntity<TaskDto> getProjectById(
      @PathVariable("projectId") @ApiParam(name = "projectId", value = "Id of project, which task we need to obtain.")
          Long projectId,
      @PathVariable("taskId") @ApiParam(name = "taskId", value = "Id of task we need to obtain.")
          Long taskId) {
    return ResponseEntity.ok(taskService.findTaskById(taskId, projectId));
  }

  /**
   * createTask endpoint.
   *
   * @param taskCreateDto Data-transfer object of Task that will be transformed to Task and
   *     put into DB.
   * @param projectId id of project, on which we want to put new task.
   * @return ResponseEntity<Long> 201 HTTP code and id of created Task.
   */
  @ApiOperation(
      value = "Create Task.",
      notes = "Creates new task and saves that one into DB.",
      response = Long.class)
  @ApiResponses(
      value = {
          @ApiResponse(code = 201, message = "Successfully created task."),
          @ApiResponse(
              code = 403,
              message = "Accessing the resource you were trying to reach is forbidden"),
          @ApiResponse(code = 400, message = "Field is not valid!")
      })
  @Transactional
  @PostMapping("/{projectId}")
  @ResponseStatus(value = HttpStatus.CREATED)
  public ResponseEntity<Long> createTask(
      @RequestBody
      @Valid
      @ApiParam(name = "taskCreateDto", value = "Dto of task we want to create and save.")
          TaskCreateDto taskCreateDto,
      @PathVariable("projectId") @ApiParam(name = "projectId", value = "Id of project, on which we want to put new task.")
          Long projectId) {
    if (log.isDebugEnabled()) {
      log.debug(
          String.format(
              LogMessage.DEBUG_REQUEST_BODY_LOG,
              jsonEntitySerializer.serializeObjectToJson(taskCreateDto)));
    }

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(taskService.createTask(taskCreateDto, projectId));
  }

  /**
   * updateTask endpoint.
   *
   * @param taskUpdateDto Data-transfer object of Task that will be transformed to Task and
   *     put into DB.
   * @param projectId id of project, on which we want to update existing task.
   * @param taskId id of Task we need to update.
   * @return ResponseEntity<Long> 204 HTTP code.
   */
  @ApiOperation(
      value = "Update Task.",
      notes = "Updates existing task and saves that one into DB.",
      response = Long.class)
  @ApiResponses(
      value = {
          @ApiResponse(code = 204, message = "Successfully updated task."),
          @ApiResponse(
              code = 403,
              message = "Accessing the resource you were trying to reach is forbidden"),
          @ApiResponse(code = 400, message = "Field is not valid!"),
          @ApiResponse(code = 404, message = "Task or Project not found.")
      })
  @Transactional
  @PostMapping("/{projectId}/{taskId}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public ResponseEntity<TaskDto> updateTask(
      @RequestBody
      @Valid
      @ApiParam(name = "taskUpdateDto", value = "Dto of task we want to update and save.")
          TaskUpdateDto taskUpdateDto,
      @PathVariable("projectId") @ApiParam(name = "projectId", value = "Id of project, on which we want to update existing task.")
          Long projectId,
      @PathVariable("taskId") @ApiParam(name = "taskId", value = "Id of task which we want to update.")
          Long taskId) {
    if (log.isDebugEnabled()) {
      log.debug(
          String.format(
              LogMessage.DEBUG_REQUEST_BODY_LOG,
              jsonEntitySerializer.serializeObjectToJson(taskUpdateDto)));
    }

    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body(taskService.updateTask(taskUpdateDto, taskId, projectId));
  }

  /**
   * deleteTaskById endpoint.
   *
   * @param projectId id of project, which task we want to delete.
   * @param taskId id of task, which we want to delete.
   * @return ResponseEntity 204 HTTP code.
   */
  @ApiOperation(value = "Delete task.", notes = "Deletes task of project with id we noted.")
  @ApiResponses(
      value = {
          @ApiResponse(code = 204, message = "Successfully deleted task."),
          @ApiResponse(
              code = 403,
              message = "Accessing the resource you were trying to reach is forbidden"),
          @ApiResponse(code = 404, message = "Task or project not found.")
      })
  @Transactional
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  @DeleteMapping("/{projectId}/{taskId}")
  public ResponseEntity<Void> deleteTaskById(
      @PathVariable("projectId") @ApiParam(name = "projectId", value = "Id of project, which task we want to delete.")
          Long projectId,
      @PathVariable("taskId") @ApiParam(name = "taskId", value = "Id of task, which we want to delete.")
          Long taskId) {
    taskService.deleteTaskById(taskId, projectId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
