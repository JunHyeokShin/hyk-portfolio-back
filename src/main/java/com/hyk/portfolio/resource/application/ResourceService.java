package com.hyk.portfolio.resource.application;

import java.io.InputStream;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.portfolio.resource.application.dto.ResourceDetail;
import com.hyk.portfolio.resource.domain.Resource;
import com.hyk.portfolio.resource.domain.ResourceRepository;
import com.hyk.portfolio.resource.domain.ResourceStatus;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ResourceService {

  private final ResourceRepository resourceRepository;
  private final FileStorage fileStorage;

  public ResourceDetail uploadResource(String originalFilename, InputStream inputStream) {
    UUID id = UUID.randomUUID();
    String savedFilename = id + getExtension(originalFilename);
    String url = this.fileStorage.upload(savedFilename, inputStream);
    Resource resource = Resource.create(id, originalFilename, savedFilename, url);
    try {
      Resource savedResource = this.resourceRepository.save(resource);
      return ResourceDetail.from(savedResource);
    }
    catch (Exception e) {
      try {
        this.fileStorage.delete(savedFilename);
      }
      catch (StorageException e1) {
        log.error("Failed to delete file after database transaction rollback: Orphaned file created: {}",
            savedFilename, e1);
        throw new OrphanedFileException(savedFilename);
      }
      throw e;
    }
  }

  public void deleteExpiredResources() {
    Instant threshold = Instant.now().minus(24, ChronoUnit.HOURS);
    List<Resource> expiredResources = this.resourceRepository.findByStatusAndCreatedAtBefore(
        ResourceStatus.PENDING, threshold);
    expiredResources.forEach(resource -> {
      try {
        this.fileStorage.delete(resource.getSavedFilename());
        this.resourceRepository.delete(resource);
      }
      catch (Exception e) {
        log.error("Failed to delete expired resource: {}", resource.getSavedFilename(), e);
      }
    });
  }

  private String getExtension(String filename) {
    if (filename == null || filename.isEmpty()) {
      return "";
    }
    int lastDotIndex = filename.lastIndexOf(".");
    if (lastDotIndex <= 0 || lastDotIndex == filename.length() - 1) {
      return "";
    }
    return filename.substring(lastDotIndex).toLowerCase();
  }

}
