package com.hyk.portfolio.project.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.portfolio.common.exception.BusinessException;
import com.hyk.portfolio.project.application.port.in.WriteProjectCommand;
import com.hyk.portfolio.project.application.port.in.WriteProjectUseCase;
import com.hyk.portfolio.project.application.port.out.LoadProjectPort;
import com.hyk.portfolio.project.application.port.out.SaveProjectPort;
import com.hyk.portfolio.project.domain.exception.ProjectErrorCode;
import com.hyk.portfolio.project.domain.model.Project;
import com.hyk.portfolio.project.domain.model.Slug;

@RequiredArgsConstructor
@Transactional
@Service
class WriteProjectService implements WriteProjectUseCase {

  private final SaveProjectPort saveProjectPort;
  private final LoadProjectPort loadProjectPort;

  @Override
  public Slug write(WriteProjectCommand command) {
    if (this.loadProjectPort.existsBySlug(command.slug())) {
      throw new BusinessException(ProjectErrorCode.SLUG_DUPLICATED);
    }
    Project project = Project.write(
        command.slug(),
        command.title(),
        command.thumbnail(),
        command.themeColor(),
        command.description(),
        command.content()
    );
    Project saved = this.saveProjectPort.save(project);
    // TODO: 이벤트 발행
    return saved.getSlug();
  }

}
