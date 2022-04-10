package com.innowise.springprojectmanager.service.task.impl;

import com.innowise.springprojectmanager.model.dto.task.TaskCreateDto;
import com.innowise.springprojectmanager.model.dto.task.TaskDto;
import com.innowise.springprojectmanager.model.dto.task.TaskUpdateDto;
import com.innowise.springprojectmanager.model.entity.Project;
import com.innowise.springprojectmanager.model.entity.Task;
import com.innowise.springprojectmanager.model.entity.User;
import com.innowise.springprojectmanager.model.enumeration.Role;
import com.innowise.springprojectmanager.model.enumeration.SortBy;
import com.innowise.springprojectmanager.repository.ProjectRepository;
import com.innowise.springprojectmanager.repository.TaskRepository;
import com.innowise.springprojectmanager.service.task.TaskService;
import com.innowise.springprojectmanager.utils.DateParser;
import com.innowise.springprojectmanager.utils.JwtDecoder;
import com.innowise.springprojectmanager.utils.exception.EntityNotFoundException;
import com.innowise.springprojectmanager.utils.exception.ValidationException;
import com.innowise.springprojectmanager.utils.literal.ExceptionMessage;
import com.innowise.springprojectmanager.utils.literal.LogMessage;
import com.innowise.springprojectmanager.utils.mapper.task.TaskDtoMapper;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
  private final JwtDecoder jwtDecoder;
  private final TaskRepository taskRepository;
  private final ProjectRepository projectRepository;
  private final TaskDtoMapper taskDtoMapper;
  private final DateParser dateParser;

  @Override
  public TaskDto findTaskById(Long taskId, Long projectId) {
    User authenticatedUser = jwtDecoder.getLoggedUser();

    Optional<Task> foundTask;
    Optional<Project> foundProjectOptional;
    Project foundProject;

    if(authenticatedUser.getRole().equals(Role.USER)){
      foundProjectOptional = projectRepository.findProjectByIdAndUserAndDeleted(projectId, authenticatedUser, false);
    } else {
      foundProjectOptional = projectRepository.findProjectByIdAndDeleted(projectId, false);
    }

    if (!foundProjectOptional.isPresent()) {
      log.error(String.format(LogMessage.PROJECT_NOT_FOUND_LOG, projectId));
      throw new EntityNotFoundException(ExceptionMessage.PROJECT_NOT_FOUND);
    } else {
      foundProject = foundProjectOptional.get();
    }

    foundTask = taskRepository.findTaskByIdAndProjectAndDeleted(taskId, foundProject, false);

    if (!foundTask.isPresent()) {
      log.error(String.format(LogMessage.TASK_NOT_FOUND_LOG, taskId));
      throw new EntityNotFoundException(ExceptionMessage.TASK_NOT_FOUND);
    } else {
      return taskDtoMapper.toDto(foundTask.get());
    }
  }

  @Override
  public List<TaskDto> findAndSortAllTasks(Long projectId, String sortBy, String issuedAtMin, String issuedAtMax, String completed) {
   return filterAndSortTasks(getAllTasks(projectId), sortBy, issuedAtMin, issuedAtMax, completed);
  }

  private List<Task> getAllTasks(Long projectId){
    User authenticatedUser = jwtDecoder.getLoggedUser();

    Optional<Project> foundProjectOptional;
    Project foundProject;

    if(authenticatedUser.getRole().equals(Role.USER)){
      foundProjectOptional = projectRepository.findProjectByIdAndUserAndDeleted(projectId, authenticatedUser, false);
    } else {
      foundProjectOptional = projectRepository.findProjectByIdAndDeleted(projectId, false);
    }

    if (!foundProjectOptional.isPresent()) {
      log.error(String.format(LogMessage.PROJECT_NOT_FOUND_LOG, projectId));
      throw new EntityNotFoundException(ExceptionMessage.PROJECT_NOT_FOUND);
    } else {
      foundProject = foundProjectOptional.get();
    }

    return taskRepository.findTasksByProjectAndDeleted(foundProject, false);
  }

  private List<TaskDto> filterAndSortTasks(List<Task> tasks, String sortBy, String issuedAtMin, String issuedAtMax, String completed){
    if(sortBy != null && sortBy.equals(SortBy.TITLE_ASC.getSortString())) {
      tasks = tasks.stream()
          .sorted(Comparator.comparing(Task::getTitle))
          .collect(Collectors.toList());
    } else if(sortBy != null && sortBy.equals(SortBy.TITLE_DESC.getSortString())){
      tasks = tasks.stream()
          .sorted(Comparator.comparing(Task::getTitle)
              .reversed())
          .collect(Collectors.toList());
    }

    if(issuedAtMin != null && !issuedAtMin.isEmpty()){
      tasks = tasks.stream()
          .filter(project -> project.getIssuedAt().isAfter(dateParser.stringToLocalDateTime(issuedAtMin)))
          .collect(Collectors.toList());
    }

    if(issuedAtMax != null && !issuedAtMax.isEmpty()){
      tasks = tasks.stream()
          .filter(project -> project.getIssuedAt().isBefore(dateParser.stringToLocalDateTime(issuedAtMax)))
          .collect(Collectors.toList());
    }

    if(completed != null && !completed.isEmpty()){
      tasks = tasks.stream()
          .filter(task -> task.isCompleted() == Boolean.parseBoolean(completed))
          .collect(Collectors.toList());
    }

    return tasks.stream().map(taskDtoMapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public Long createTask(TaskCreateDto taskCreateDto, Long projectId) {
    User authenticatedUser = jwtDecoder.getLoggedUser();

    Optional<Project> foundProjectOptional;
    Project foundProject;

    if(authenticatedUser.getRole().equals(Role.USER)){
      foundProjectOptional = projectRepository.findProjectByIdAndUserAndDeleted(projectId, authenticatedUser, false);
    } else {
      foundProjectOptional = projectRepository.findProjectByIdAndDeleted(projectId, false);
    }

    if (!foundProjectOptional.isPresent()) {
      log.error(String.format(LogMessage.PROJECT_NOT_FOUND_LOG, projectId));
      throw new EntityNotFoundException(ExceptionMessage.PROJECT_NOT_FOUND);
    } else {
      foundProject = foundProjectOptional.get();
    }

    Task taskToCreate = new Task();
    taskToCreate.setProject(foundProject);
    taskToCreate.setTitle(taskCreateDto.getTitle());
    taskToCreate.setDescription(taskCreateDto.getDescription());
    taskToCreate.setIssuedAt(LocalDateTime.now());
    taskToCreate.setExpiresAt(dateParser.stringToLocalDateTime(taskCreateDto.getExpiresAt()));
    taskToCreate.setCompleted(false);

    return taskRepository.save(taskToCreate).getId();
  }

  @Override
  public TaskDto updateTask(TaskUpdateDto taskUpdateDto, Long taskId, Long projectId) {
    Optional<Project> foundProjectOptional;
    Project foundProject;

    User authenticatedUser = jwtDecoder.getLoggedUser();

    if(authenticatedUser.getRole().equals(Role.USER)){
      foundProjectOptional = projectRepository.findProjectByIdAndUserAndDeleted(projectId, authenticatedUser, false);
    } else {
      foundProjectOptional = projectRepository.findProjectByIdAndDeleted(projectId, false);
    }

    if (!foundProjectOptional.isPresent()) {
      log.error(String.format(LogMessage.PROJECT_NOT_FOUND_LOG, projectId));
      throw new EntityNotFoundException(ExceptionMessage.PROJECT_NOT_FOUND);
    } else {
      foundProject = foundProjectOptional.get();
    }

    Optional<Task> taskToUpdateOptional = taskRepository.findTaskByIdAndProjectAndDeleted(taskId, foundProject, false);
    Task taskToUpdate;
    if (!taskToUpdateOptional.isPresent()) {
      log.error(String.format(LogMessage.TASK_NOT_FOUND_LOG, projectId));
      throw new EntityNotFoundException(ExceptionMessage.TASK_NOT_FOUND);
    } else {
      taskToUpdate = taskToUpdateOptional.get();
    }

    taskToUpdate.setTitle(taskUpdateDto.getTitle());
    taskToUpdate.setDescription(taskUpdateDto.getDescription());
    taskToUpdate.setExpiresAt(dateParser.stringToLocalDateTime(taskUpdateDto.getExpiresAt()));

    return taskDtoMapper.toDto(taskRepository.save(taskToUpdate));
  }

  @Override
  public void deleteTaskById(Long taskId, Long projectId) {
    User authenticatedUser = jwtDecoder.getLoggedUser();
    Optional<Project> foundProjectOptional = projectRepository.findProjectByIdAndDeleted(projectId, false);

    if (!foundProjectOptional.isPresent()) {
      log.error(String.format(LogMessage.PROJECT_NOT_FOUND_LOG, projectId));
      throw new EntityNotFoundException(ExceptionMessage.PROJECT_NOT_FOUND);
    } else {
      Project foundProject = foundProjectOptional.get();
      if (authenticatedUser.getRole().equals(Role.USER)
          && !authenticatedUser.getId().equals(foundProject.getUser().getId())) {
        log.error(
            String.format(
                LogMessage.ID_OF_LOGGED_USER_NOT_EQUALS_ID_OF_PROJECT_LOG,
                authenticatedUser.getId(),
                foundProject.getUser().getId()));
        throw new ValidationException(ExceptionMessage.ID_OF_LOGGED_USER_NOT_EQUALS_ID_OF_PROJECT);
      } else {
        Optional<Task> taskToDeleteOptional = taskRepository.findTaskByIdAndProjectAndDeleted(taskId, foundProject, false);

        Task taskToDelete;
        if (!taskToDeleteOptional.isPresent()) {
          log.error(String.format(LogMessage.TASK_NOT_FOUND_LOG, projectId));
          throw new EntityNotFoundException(ExceptionMessage.TASK_NOT_FOUND);
        } else {
          taskToDelete = taskToDeleteOptional.get();
        }

        log.info(String.format(LogMessage.TASK_DELETED_LOG, projectId));
        taskToDelete.setDeleted(true);
        taskRepository.save(taskToDelete);
      }
    }
  }

  @Override
  public Long completeTask(Long taskId, Long projectId) {
    Optional<Project> foundProjectOptional;
    Project foundProject;

    User authenticatedUser = jwtDecoder.getLoggedUser();

    if(authenticatedUser.getRole().equals(Role.USER)){
      foundProjectOptional = projectRepository.findProjectByIdAndUserAndDeleted(projectId, authenticatedUser, false);
    } else {
      foundProjectOptional = projectRepository.findProjectByIdAndDeleted(projectId, false);
    }

    if (!foundProjectOptional.isPresent()) {
      log.error(String.format(LogMessage.PROJECT_NOT_FOUND_LOG, projectId));
      throw new EntityNotFoundException(ExceptionMessage.PROJECT_NOT_FOUND);
    } else {
      foundProject = foundProjectOptional.get();
    }

    Optional<Task> taskToCompleteOptional = taskRepository.findTaskByIdAndProjectAndDeleted(taskId, foundProject, false);
    Task taskToComplete;
    if (!taskToCompleteOptional.isPresent()) {
      log.error(String.format(LogMessage.TASK_NOT_FOUND_LOG, projectId));
      throw new EntityNotFoundException(ExceptionMessage.TASK_NOT_FOUND);
    } else {
      taskToComplete = taskToCompleteOptional.get();
    }

    taskToComplete.setCompleted(true);

    return taskDtoMapper.toDto(taskRepository.save(taskToComplete)).getId();
  }
}
