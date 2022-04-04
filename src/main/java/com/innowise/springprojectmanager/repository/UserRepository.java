package com.innowise.springprojectmanager.repository;

import com.innowise.springprojectmanager.model.entity.User;
import com.innowise.springprojectmanager.model.enumeration.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA User repository class.
 *
 * @author Edvard Krainiy on 02/04/2022
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findUserByUsername(String username);

  Optional<User> findUserByEmail(String email);

  Optional<User> findUserByRole(Role role);

  Optional<User> findUserById(Long userId);

}
