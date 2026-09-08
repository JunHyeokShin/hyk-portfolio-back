package com.hyk.portfolio.resource.application.service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import com.hyk.portfolio.resource.application.port.in.PurgeResourcesUseCase;
import com.hyk.portfolio.resource.application.port.out.LoadResourcePort;
import com.hyk.portfolio.resource.application.port.out.StorageException;
import com.hyk.portfolio.resource.domain.model.Resource;

@Slf4j
@EnableConfigurationProperties(ResourcePurgeProperties.class)
@Service
class PurgeResourcesService implements PurgeResourcesUseCase {

  private final Duration retention;
  private final LoadResourcePort loadResourcePort;
  private final ResourcePurger resourcePurger;

  PurgeResourcesService(
      ResourcePurgeProperties properties,
      LoadResourcePort loadResourcePort,
      ResourcePurger resourcePurger
  ) {
    this.retention = properties.retention();
    this.loadResourcePort = loadResourcePort;
    this.resourcePurger = resourcePurger;
  }

  @Override
  public int purge() {
    Instant threshold = Instant.now().minus(this.retention);
    List<Resource> orphans = this.loadResourcePort.findAllPendingUploadedBefore(threshold);

    int deleted = 0;
    for (Resource resource : orphans) {
      try {
        this.resourcePurger.purge(resource);
        deleted++;
      }
      catch (StorageException e) {
        log.warn("리소스 파일 삭제 실패, 건너뜁니다: {}", resource.getFilename(), e);
      }
    }
    return deleted;
  }

}
