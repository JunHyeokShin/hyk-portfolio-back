package com.hyk.portfolio.resource.application.service;

import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.portfolio.resource.application.port.in.SyncResourcesCommand;
import com.hyk.portfolio.resource.application.port.in.SyncResourcesUseCase;
import com.hyk.portfolio.resource.application.port.out.LoadResourcePort;
import com.hyk.portfolio.resource.application.port.out.SaveResourcePort;
import com.hyk.portfolio.resource.domain.model.Resource;
import com.hyk.portfolio.resource.domain.model.Target;

@RequiredArgsConstructor
@Transactional
@Service
class SyncResourcesService implements SyncResourcesUseCase {

  private final SaveResourcePort saveResourcePort;
  private final LoadResourcePort loadResourcePort;

  @Override
  public void sync(SyncResourcesCommand command) {
    Target target = command.target();
    Set<String> urls = command.urls();

    for (Resource resource : this.loadResourcePort.findAllByTarget(target)) {
      if (!urls.contains(resource.getUrl())) {
        resource.detach();
        this.saveResourcePort.save(resource);
      }
    }
    if (urls.isEmpty()) {
      return;
    }
    for (Resource resource : this.loadResourcePort.findAllByUrlIn(urls)) {
      resource.attach(target);
      this.saveResourcePort.save(resource);
    }
  }

}
