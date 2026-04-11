package com.hyk.portfolio.project.domain;

import java.util.Optional;

public interface ProjectRepository {

  Project save(Project project);

  boolean existsById(ProjectId id);

  Optional<Project> findById(ProjectId id);

  void delete(Project project);

}
