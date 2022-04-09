package com.innowise.springprojectmanager.model.entity;

import com.innowise.springprojectmanager.utils.literal.JpaMappingDetails;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Basic Project class.
 *
 * @author Edvard Krainiy on 02/04/2022
 */
@Entity
@Getter
@Setter
@Table(name = JpaMappingDetails.PROJECTS_TABLE)
@NoArgsConstructor
@AllArgsConstructor
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = JpaMappingDetails.ID, nullable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
  @JoinColumn(name = JpaMappingDetails.USER_ID)
  private User user;

  @Column(name = JpaMappingDetails.TITLE)
  private String title;

  @Column(name = JpaMappingDetails.DESCRIPTION)
  private String description;

  @Column(name = JpaMappingDetails.ISSUED_AT)
  private LocalDateTime issuedAt;

  @Column(name = JpaMappingDetails.DELETED)
  private boolean deleted;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = JpaMappingDetails.PROJECT)
  private Set<Task> tasks;
}
