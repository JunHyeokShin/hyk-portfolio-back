package com.hyk.portfolio.resource.adapter.in.event;

import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import com.hyk.portfolio.project.domain.event.ProjectDeleted;
import com.hyk.portfolio.resource.application.port.in.SyncResourcesCommand;
import com.hyk.portfolio.resource.application.port.in.SyncResourcesUseCase;
import com.hyk.portfolio.resource.domain.model.Target;
import com.hyk.portfolio.resource.domain.model.TargetType;

@RequiredArgsConstructor
@Component
class ProjectDeletedListener {

  private final SyncResourcesUseCase syncResourcesUseCase;

  @ApplicationModuleListener
  void on(ProjectDeleted event) {
    this.syncResourcesUseCase.sync(new SyncResourcesCommand(
        Target.of(TargetType.PROJECT, event.projectId()), Set.of()));
  }

}
