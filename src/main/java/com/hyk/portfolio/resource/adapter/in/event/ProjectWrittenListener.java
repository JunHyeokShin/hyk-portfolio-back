package com.hyk.portfolio.resource.adapter.in.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.hyk.portfolio.project.domain.event.ProjectWritten;
import com.hyk.portfolio.resource.application.port.in.AttachResourcesCommand;
import com.hyk.portfolio.resource.application.port.in.AttachResourcesUseCase;
import com.hyk.portfolio.resource.domain.model.Target;
import com.hyk.portfolio.resource.domain.model.TargetType;

@RequiredArgsConstructor
@Component
class ProjectWrittenListener {

  private final AttachResourcesUseCase attachResourcesUseCase;

  @EventListener
  void on(ProjectWritten event) {
    this.attachResourcesUseCase.attach(new AttachResourcesCommand(
        Target.of(TargetType.PROJECT, event.projectId()), event.referencedUrls()));
  }

}
