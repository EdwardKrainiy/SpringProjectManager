package com.innowise.springprojectmanager.utils.mapper.project;

import com.innowise.springprojectmanager.model.dto.project.ProjectDto;
import com.innowise.springprojectmanager.model.entity.Project;
import com.innowise.springprojectmanager.utils.mapper.task.TaskDtoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * DtoMapper interface, which contains methods to transform Project and ProjectDto both ways.
 *
 * @author Edvard Krainiy on 04/04/2022
 */
@Mapper(componentModel = "spring", uses = TaskDtoMapper.class)
public interface ProjectDtoMapper {

  /**
   * ProjectToProjectDto method. Converts Project object to ProjectDto.
   *
   * @param project Project object we need to convert.
   * @return ProjectDto Obtained ProjectDto.
   */
  @Mapping(source = "project.user.id", target = "userId")
  ProjectDto toDto(Project project);

  /**
   * ProjectDtoToProject method. Converts Project to Project object.
   *
   * @param projectDto ProjectDto we need to convert.
   * @return Project Obtained Project object.
   */
  Project toEntity(ProjectDto projectDto);
}
