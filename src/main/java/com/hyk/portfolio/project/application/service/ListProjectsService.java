package com.hyk.portfolio.project.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.portfolio.project.application.port.in.ListProjectsUseCase;
import com.hyk.portfolio.project.application.port.in.ProjectSummary;
import com.hyk.portfolio.project.application.port.out.LoadProjectPort;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
class ListProjectsService implements ListProjectsUseCase {

  private final LoadProjectPort loadProjectPort;

  @Override
  public Page<ProjectSummary> list(String keyword, Pageable pageable) {
    return this.loadProjectPort.findAll(keyword, pageable);
  }

}
