package com.innowise.springprojectmanager.service.project.impl;

import com.innowise.springprojectmanager.model.dto.project.ProjectCreateDto;
import com.innowise.springprojectmanager.model.dto.project.ProjectDto;
import com.innowise.springprojectmanager.model.dto.project.ProjectUpdateDto;
import com.innowise.springprojectmanager.model.dto.task.TaskCreateDto;
import com.innowise.springprojectmanager.model.entity.Project;
import com.innowise.springprojectmanager.model.entity.Task;
import com.innowise.springprojectmanager.model.entity.User;
import com.innowise.springprojectmanager.model.enumeration.Role;
import com.innowise.springprojectmanager.repository.ProjectRepository;
import com.innowise.springprojectmanager.repository.TaskRepository;
import com.innowise.springprojectmanager.service.project.ProjectService;
import com.innowise.springprojectmanager.utils.DateParser;
import com.innowise.springprojectmanager.utils.JwtDecoder;
import com.innowise.springprojectmanager.utils.exception.EntityNotFoundException;
import com.innowise.springprojectmanager.utils.literal.ExceptionMessage;
import com.innowise.springprojectmanager.utils.literal.LogMessage;
import com.innowise.springprojectmanager.utils.mapper.project.ProjectDtoMapper;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
  private final JwtDecoder jwtDecoder;
  private final ProjectRepository projectRepository;
  private final ProjectDtoMapper projectDtoMapper;
  private final DateParser dateParser;
  private final TaskRepository taskRepository;

  @Override
  public ProjectDto findProjectById(Long projectId) {
    User authenticatedUser = jwtDecoder.getLoggedUser();

    Optional<Project> foundProject;

    if (authenticatedUser.getRole().equals(Role.USER)) {
      foundProject = projectRepository.findProjectByIdAndUser(projectId, authenticatedUser);
    } else {
      foundProject = projectRepository.findProjectById(projectId);
    }

    if (!foundProject.isPresent()) {
      log.error(String.format(LogMessage.PROJECT_NOT_FOUND_LOG, projectId));
      throw new EntityNotFoundException(ExceptionMessage.PROJECT_NOT_FOUND);
    } else {
      return projectDtoMapper.toDto(foundProject.get());
    }
  }

  @Override
  public Long createProject(ProjectCreateDto projectCreateDto) {
    User authenticatedUser = jwtDecoder.getLoggedUser();
    Set<Task> tasksToCreate = new LinkedHashSet<>();

    Project projectToCreate = new Project();

    for(TaskCreateDto taskDto: projectCreateDto.getTasks()){
      Task taskToAdd = new Task();
      taskToAdd.setTitle(taskDto.getTitle());
      taskToAdd.setDescription(taskDto.getDescription());
      taskToAdd.setIssuedAt(LocalDateTime.now());
      taskToAdd.setExpiresAt(dateParser.stringToLocalDateTime(taskDto.getExpiresAt()));
      taskToAdd.setProject(projectToCreate);
      tasksToCreate.add(taskToAdd);
      taskRepository.save(taskToAdd);
    }

    projectToCreate.setTitle(projectCreateDto.getTitle());
    projectToCreate.setDescription(projectCreateDto.getDescription());
    projectToCreate.setIssuedAt(LocalDateTime.now());
    projectToCreate.setUser(authenticatedUser);
    projectToCreate.setTasks(tasksToCreate);

    Long createdProjectId = projectRepository.save(projectToCreate).getId();
    log.info(
        String.format(
            LogMessage.PROJECT_CREATED_LOG, createdProjectId));
    return createdProjectId;
  }

  @Override
  public ProjectDto updateAccount(ProjectUpdateDto projectUpdateDto, Long projectId) {
    return null;
  }

  @Override
  public List<ProjectDto> findAllProjects() {
    return getProjectDtos();
  }

  private List<ProjectDto> getProjectDtos() {
    User authenticatedUser = jwtDecoder.getLoggedUser();
    List<Project> projects;

    if (authenticatedUser.getRole().equals(Role.USER)) {
      projects = projectRepository.findProjectsByUser(authenticatedUser);
    } else {
      projects = projectRepository.findAll();
    }

    return projects.stream().map(projectDtoMapper::toDto).collect(Collectors.toList());
  }
}
