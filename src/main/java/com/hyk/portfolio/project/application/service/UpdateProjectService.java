package com.hyk.portfolio.project.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.portfolio.common.exception.BusinessException;
import com.hyk.portfolio.project.application.port.in.UpdateProjectCommand;
import com.hyk.portfolio.project.application.port.in.UpdateProjectUseCase;
import com.hyk.portfolio.project.application.port.out.LoadProjectPort;
import com.hyk.portfolio.project.application.port.out.PublishProjectEventPort;
import com.hyk.portfolio.project.application.port.out.SaveProjectPort;
import com.hyk.portfolio.project.domain.event.ProjectUpdated;
import com.hyk.portfolio.project.domain.exception.ProjectErrorCode;
import com.hyk.portfolio.project.domain.model.Project;
import com.hyk.portfolio.project.domain.model.Slug;

@RequiredArgsConstructor
@Transactional
@Service
class UpdateProjectService implements UpdateProjectUseCase {

  private final SaveProjectPort saveProjectPort;
  private final LoadProjectPort loadProjectPort;
  private final PublishProjectEventPort publishProjectEventPort;

  @Override
  public Slug update(Slug slug, UpdateProjectCommand command) {
    Project project = this.loadProjectPort.findBySlug(slug)
        .orElseThrow(() -> new BusinessException(ProjectErrorCode.PROJECT_NOT_FOUND));
    if (this.loadProjectPort.existsBySlugAndIdNot(command.slug(), project.getId())) {
      throw new BusinessException(ProjectErrorCode.SLUG_DUPLICATED);
    }
    project.update(
        command.slug(),
        command.title(),
        command.thumbnail(),
        command.themeColor(),
        command.description(),
        command.content()
    );
    Project saved = this.saveProjectPort.save(project);
    this.publishProjectEventPort.publish(
        new ProjectUpdated(saved.getId(), saved.referencedUrls()));
    return saved.getSlug();
  }

}
