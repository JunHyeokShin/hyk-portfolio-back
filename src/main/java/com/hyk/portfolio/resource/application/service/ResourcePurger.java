package com.hyk.portfolio.resource.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.portfolio.resource.application.port.out.DeleteFilePort;
import com.hyk.portfolio.resource.application.port.out.DeleteResourcePort;
import com.hyk.portfolio.resource.domain.model.Resource;

@RequiredArgsConstructor
@Component
class ResourcePurger {

  private final DeleteFilePort deleteFilePort;
  private final DeleteResourcePort deleteResourcePort;

  @Transactional
  void purge(Resource resource) {
    this.deleteFilePort.delete(resource.getFilename());
    this.deleteResourcePort.delete(resource);
  }

}
