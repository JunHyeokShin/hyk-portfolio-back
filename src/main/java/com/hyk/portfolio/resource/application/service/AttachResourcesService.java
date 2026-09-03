package com.hyk.portfolio.resource.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.portfolio.resource.application.port.in.AttachResourcesCommand;
import com.hyk.portfolio.resource.application.port.in.AttachResourcesUseCase;
import com.hyk.portfolio.resource.application.port.out.LoadResourcePort;
import com.hyk.portfolio.resource.application.port.out.SaveResourcePort;
import com.hyk.portfolio.resource.domain.model.Resource;

@RequiredArgsConstructor
@Transactional
@Service
class AttachResourcesService implements AttachResourcesUseCase {

  private final SaveResourcePort saveResourcePort;
  private final LoadResourcePort loadResourcePort;

  @Override
  public void attach(AttachResourcesCommand command) {
    if (command.urls().isEmpty()) {
      return;
    }
    for (Resource resource : this.loadResourcePort.findAllByUrlIn(command.urls())) {
      resource.attach(command.target());
      this.saveResourcePort.save(resource);
    }
  }

}
