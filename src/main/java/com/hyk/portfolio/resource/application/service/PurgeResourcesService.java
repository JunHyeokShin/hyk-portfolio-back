package com.hyk.portfolio.resource.application.service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.portfolio.resource.application.port.in.PurgeResourcesUseCase;
import com.hyk.portfolio.resource.application.port.out.DeleteFilePort;
import com.hyk.portfolio.resource.application.port.out.DeleteResourcePort;
import com.hyk.portfolio.resource.application.port.out.LoadResourcePort;
import com.hyk.portfolio.resource.application.port.out.StorageException;
import com.hyk.portfolio.resource.domain.model.Resource;

@Slf4j
@EnableConfigurationProperties(ResourcePurgeProperties.class)
@Transactional
@Service
class PurgeResourcesService implements PurgeResourcesUseCase {

  private final Duration retention;
  private final DeleteFilePort deleteFilePort;
  private final LoadResourcePort loadResourcePort;
  private final DeleteResourcePort deleteResourcePort;

  PurgeResourcesService(
      ResourcePurgeProperties properties,
      DeleteFilePort deleteFilePort,
      LoadResourcePort loadResourcePort,
      DeleteResourcePort deleteResourcePort
  ) {
    this.retention = properties.retention();
    this.deleteFilePort = deleteFilePort;
    this.loadResourcePort = loadResourcePort;
    this.deleteResourcePort = deleteResourcePort;
  }

  @Override
  public int purge() {
    Instant threshold = Instant.now().minus(this.retention);
    List<Resource> orphans = this.loadResourcePort.findAllPendingUploadedBefore(threshold);

    int deleted = 0;
    for (Resource resource : orphans) {
      try {
        this.deleteFilePort.delete(resource.getFilename());
      }
      catch (StorageException e) {
        log.warn("리소스 파일 삭제 실패, 건너뜁니다: {}", resource.getFilename(), e);
        continue;
      }
      this.deleteResourcePort.delete(resource);
      deleted++;
    }
    return deleted;
  }

}
