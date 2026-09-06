package com.hyk.portfolio.project.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.portfolio.common.exception.BusinessException;
import com.hyk.portfolio.project.application.port.in.DeleteProjectUseCase;
import com.hyk.portfolio.project.application.port.out.DeleteProjectPort;
import com.hyk.portfolio.project.application.port.out.LoadProjectPort;
import com.hyk.portfolio.project.application.port.out.PublishProjectEventPort;
import com.hyk.portfolio.project.domain.event.ProjectDeleted;
import com.hyk.portfolio.project.domain.exception.ProjectErrorCode;
import com.hyk.portfolio.project.domain.model.Project;
import com.hyk.portfolio.project.domain.model.Slug;

@RequiredArgsConstructor
@Transactional
@Service
class DeleteProjectService implements DeleteProjectUseCase {

  private final LoadProjectPort loadProjectPort;
  private final DeleteProjectPort deleteProjectPort;
  private final PublishProjectEventPort publishProjectEventPort;

  @Override
  public void delete(Slug slug) {
    Project project = this.loadProjectPort.findBySlug(slug)
        .orElseThrow(() -> new BusinessException(ProjectErrorCode.PROJECT_NOT_FOUND));
    this.deleteProjectPort.delete(project);
    this.publishProjectEventPort.publish(new ProjectDeleted(project.getId()));
  }

}
