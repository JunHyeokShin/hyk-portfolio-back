package com.hyk.portfolio.project.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hyk.portfolio.project.domain.Project;
import com.hyk.portfolio.project.domain.ProjectId;

public interface JpaProjectRepository extends JpaRepository<Project, ProjectId> {

}
