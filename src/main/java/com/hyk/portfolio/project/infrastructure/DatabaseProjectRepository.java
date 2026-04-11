package com.hyk.portfolio.project.infrastructure;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.hyk.portfolio.project.domain.Project;
import com.hyk.portfolio.project.domain.ProjectId;
import com.hyk.portfolio.project.domain.ProjectRepository;

@RequiredArgsConstructor
@Repository
public class DatabaseProjectRepository implements ProjectRepository {

  private final JpaProjectRepository jpaProjectRepository;

  @Override
  public Project save(Project project) {
    return this.jpaProjectRepository.save(project);
  }

  @Override
  public boolean existsById(ProjectId id) {
    return this.jpaProjectRepository.existsById(id);
  }

  @Override
  public Optional<Project> findById(ProjectId id) {
    return this.jpaProjectRepository.findById(id);
  }

  @Override
  public void delete(Project project) {
    this.jpaProjectRepository.delete(project);
  }

}
