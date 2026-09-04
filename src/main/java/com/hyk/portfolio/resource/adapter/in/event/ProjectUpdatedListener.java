package com.hyk.portfolio.resource.adapter.in.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.hyk.portfolio.project.domain.event.ProjectUpdated;
import com.hyk.portfolio.resource.application.port.in.SyncResourcesCommand;
import com.hyk.portfolio.resource.application.port.in.SyncResourcesUseCase;
import com.hyk.portfolio.resource.domain.model.Target;
import com.hyk.portfolio.resource.domain.model.TargetType;

@RequiredArgsConstructor
@Component
class ProjectUpdatedListener {

  private final SyncResourcesUseCase syncResourcesUseCase;

  @EventListener
  void on(ProjectUpdated event) {
    this.syncResourcesUseCase.sync(new SyncResourcesCommand(
        Target.of(TargetType.PROJECT, event.projectId()), event.referencedUrls()));
  }

}
