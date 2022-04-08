package com.innowise.springprojectmanager.controller;

import com.innowise.springprojectmanager.model.dto.project.ProjectCreateDto;
import com.innowise.springprojectmanager.model.dto.project.ProjectDto;
import com.innowise.springprojectmanager.model.dto.project.ProjectUpdateDto;
import com.innowise.springprojectmanager.service.project.ProjectService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController. Created to obtain all info about users.
 *
 * @author Edvard Krainiy on 04/04/2022
 */
@RestController
@Log4j2
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

  private final ProjectService projectService;
  private final JsonEntitySerializer jsonEntitySerializer;

  /**
   * getAllProjects endpoint.
   *
   * @return List<Project> All projects in database.
   */
  @ApiOperation(
      value = "Obtain all projects.",
      notes = "Returns all projects, stored in DB",
      produces = "application/json",
      response = Iterable.class)
  @ApiResponses(
      value = {
          @ApiResponse(code = 200, message = "Successfully retrieved list of projects."),
          @ApiResponse(
              code = 403,
              message = "Accessing the resource you were trying to reach is forbidden")
      })
  @GetMapping
  public ResponseEntity<List<ProjectDto>> getAllProjects() {
    return ResponseEntity.ok(projectService.findAllProjects());
  }

  /**
   * getProjectById endpoint.
   *
   * @param projectId id of project we want to get.
   * @return ResponseEntity<ProjectDto> Response with HTTP code and ProjectDto of project that we
   *     found.
   */
  @ApiOperation(
      value = "Obtain project by id.",
      notes = "Returns project with id we noted.",
      produces = "application/json",
      response = ProjectDto.class)
  @ApiResponses(
      value = {
          @ApiResponse(code = 200, message = "Successfully retrieved project."),
          @ApiResponse(
              code = 403,
              message = "Accessing the resource you were trying to reach is forbidden"),
          @ApiResponse(code = 404, message = "Project with this id not found.")
      })
  @GetMapping("/{id}")
  public ResponseEntity<ProjectDto> getProjectById(
      @PathVariable("id") @ApiParam(name = "id", value = "Id of project we need to obtain.")
          Long projectId) {
    return ResponseEntity.ok(projectService.findProjectById(projectId));
  }

  /**
   * createProject endpoint.
   *
   * @param projectCreateDto Data-transfer object of Project that will be transformed to Project and
   *     put into DB.
   * @return ResponseEntity<Long> 201 HTTP code and id of created Project.
   */
  @ApiOperation(
      value = "Create Project.",
      notes = "Creates new project and saves that one into DB.",
      response = Long.class)
  @ApiResponses(
      value = {
          @ApiResponse(code = 201, message = "Successfully created project."),
          @ApiResponse(
              code = 403,
              message = "Accessing the resource you were trying to reach is forbidden"),
          @ApiResponse(code = 400, message = "Field is not valid!")
      })
  @Transactional
  @PostMapping
  @ResponseStatus(value = HttpStatus.CREATED)
  public ResponseEntity<Long> createProject(
      @RequestBody
      @Valid
      @ApiParam(name = "projectCreateDto", value = "Dto of project we want to create and save.")
          ProjectCreateDto projectCreateDto) {
    if (log.isDebugEnabled()) {
      log.debug(
          String.format(
              LogMessage.DEBUG_REQUEST_BODY_LOG,
              jsonEntitySerializer.serializeObjectToJson(projectCreateDto)));
    }

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(projectService.createProject(projectCreateDto));
  }

  /**
   * updateProject endpoint.
   *
   * @param projectUpdateDto Data-transfer object of project that will be transformed to Project and
   *     updated.
   * @param projectId Id of project we need to update.
   * @return ResponseEntity<Void> 204 HTTP code.
   */
  @ApiOperation(
      value = "Update project.",
      notes = "Updates project with id we noted.",
      produces = "application/json",
      response = ProjectDto.class)
  @ApiResponses(
      value = {
          @ApiResponse(code = 204, message = "Successfully updated project."),
          @ApiResponse(
              code = 403,
              message = "Accessing the resource you were trying to reach is forbidden"),
          @ApiResponse(code = 400, message = "Field is not valid!"),
          @ApiResponse(code = 404, message = "Project not found.")
      })
  @Transactional
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  @PutMapping("/{id}")
  public ResponseEntity<ProjectDto> updateProject(
      @RequestBody
      @Valid
      @ApiParam(
          name = "projectUpdateDto",
          value = "Dto of project, which we use to update other project.")
          ProjectUpdateDto projectUpdateDto,
      @PathVariable("id") @ApiParam(name = "id", value = "Id of project we want to update.")
          Long projectId) {

    if (log.isDebugEnabled()) {
      log.debug(
          String.format(
              LogMessage.DEBUG_REQUEST_BODY_LOG,
              jsonEntitySerializer.serializeObjectToJson(projectUpdateDto)));
    }

    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body(projectService.updateProject(projectUpdateDto, projectId));
  }

  /**
   * deleteProjectById endpoint.
   *
   * @param projectId id of project we want to delete.
   * @return ResponseEntity 204 HTTP code.
   */
  @ApiOperation(value = "Delete project.", notes = "Deletes project with id we noted.")
  @ApiResponses(
      value = {
          @ApiResponse(code = 204, message = "Successfully deleted project."),
          @ApiResponse(
              code = 403,
              message = "Accessing the resource you were trying to reach is forbidden"),
          @ApiResponse(code = 404, message = "Project not found.")
      })
  @Transactional
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProjectById(
      @PathVariable("id") @ApiParam(name = "id", value = "Id of project we want to delete.")
          Long projectId) {
    projectService.deleteProjectById(projectId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
