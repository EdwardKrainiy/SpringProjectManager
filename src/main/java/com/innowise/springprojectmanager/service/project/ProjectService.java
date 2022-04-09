package com.innowise.springprojectmanager.service.project;

import com.innowise.springprojectmanager.model.dto.project.ProjectCreateDto;
import com.innowise.springprojectmanager.model.dto.project.ProjectDto;
import com.innowise.springprojectmanager.model.dto.project.ProjectUpdateDto;
import java.util.List;

  /**
   * ProjectService interface. Provides us different methods to work with Project objects on
   * Service layer.
   *
   * @author Edvard Krainiy on 04/04/2022
   */
  public interface ProjectService {

    /**
     * findProjectById method. Finds project by projectId and maps to DTO.
     *
     * @param projectId If of project we need to find.
     * @return ProjectDto Found projectDto object.
     */
    ProjectDto findProjectById(Long projectId);

    /**
     * findAllProjects method. Finds all projects, stored in DB.
     *
     * @return List<ProjectDto> List of all found projectDto objects.
     */
    List<ProjectDto> findAllProjects();

    /**
     * createProject method. Creates Project from projectCreateDto.
     *
     * @param projectCreateDto ProjectCreateDto object to create new Project.
     * @return ProjectDto Obtained id of created ProjectDto.
     */
    Long createProject(ProjectCreateDto projectCreateDto);

    /**
     * updateProject method. Updates project by id and projectUpdateDto entity.
     *
     * @param projectUpdateDto Project transfer object, which we need to update. This one will be
     *     converted into Project object, passed some checks and will be updated on DB.
     * @param projectId Id of project we need to update.
     */
    ProjectDto updateProject(ProjectUpdateDto projectUpdateDto, Long projectId);

    /**
     * deleteProjectByProjectId method. Deletes project by id.
     *
     * @param projectId Id of project we need to delete.
     */
    void deleteProjectById(Long projectId);

}
