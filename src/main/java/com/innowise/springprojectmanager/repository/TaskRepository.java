package com.innowise.springprojectmanager.repository;

import com.innowise.springprojectmanager.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Task repository class.
 *
 * @author Edvard Krainiy on 04/04/2022
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
