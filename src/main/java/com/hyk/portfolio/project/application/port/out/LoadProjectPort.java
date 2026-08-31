package com.hyk.portfolio.project.application.port.out;

import java.util.Optional;

import com.hyk.portfolio.project.domain.model.Project;
import com.hyk.portfolio.project.domain.model.Slug;

public interface LoadProjectPort {

  Optional<Project> findBySlug(Slug slug);

  boolean existsBySlug(Slug slug);

}
