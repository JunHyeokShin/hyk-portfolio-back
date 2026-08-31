package com.hyk.portfolio.project.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.portfolio.common.exception.BusinessException;
import com.hyk.portfolio.project.application.port.in.ReadProjectUseCase;
import com.hyk.portfolio.project.application.port.out.LoadProjectPort;
import com.hyk.portfolio.project.domain.exception.ProjectErrorCode;
import com.hyk.portfolio.project.domain.model.Project;
import com.hyk.portfolio.project.domain.model.Slug;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
class ReadProjectService implements ReadProjectUseCase {

  private final LoadProjectPort loadProjectPort;

  @Override
  public Project read(Slug slug) {
    return this.loadProjectPort.findBySlug(slug)
        .orElseThrow(() -> new BusinessException(ProjectErrorCode.PROJECT_NOT_FOUND));
  }

}
