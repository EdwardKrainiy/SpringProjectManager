package com.innowise.springprojectmanager.repository;

import com.innowise.springprojectmanager.model.entity.Project;
import com.innowise.springprojectmanager.model.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Project repository class.
 *
 * @author Edvard Krainiy on 04/04/2022
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

  Optional<Project> findProjectByIdAndUserAndDeleted(Long projectId, User authenticatedUser, boolean isDeleted);

  List<Project> findProjectsByUserAndDeleted(User authenticatedUser, boolean isDeleted);

  Optional<Project> findProjectByIdAndDeleted(Long projectId, boolean isDeleted);

}
