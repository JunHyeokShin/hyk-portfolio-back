package com.hyk.portfolio.project.application.port.out;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hyk.portfolio.project.application.port.in.ProjectSummary;
import com.hyk.portfolio.project.domain.model.Project;
import com.hyk.portfolio.project.domain.model.Slug;

public interface LoadProjectPort {

  Optional<Project> findBySlug(Slug slug);

  Page<ProjectSummary> findAll(String keyword, Pageable pageable);

  boolean existsBySlug(Slug slug);

}
