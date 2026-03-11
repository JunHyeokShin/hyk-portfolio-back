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

import com.hyk.portfolio.resource.domain.Resource;
import com.hyk.portfolio.resource.domain.ResourceRepository;
import com.hyk.portfolio.resource.domain.ResourceStatus;
import com.hyk.portfolio.resource.exception.OrphanFileException;
import com.hyk.portfolio.resource.exception.StorageException;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ResourceService {

  private final ResourceRepository resourceRepository;
  private final FileStorage fileStorage;

  public ResourceUploadResult upload(String originalFilename, InputStream inputStream) {
    UUID id = UUID.randomUUID();
    String extension = getExtension(originalFilename);
    String savedFilename = id + extension;
    String url = this.fileStorage.upload(savedFilename, inputStream);
    Resource resource = Resource.builder()
        .id(id)
        .originalFilename(originalFilename)
        .savedFilename(savedFilename)
        .url(url)
        .build();
    try {
      this.resourceRepository.save(resource);
    }
    catch (Exception e) {
      try {
        this.fileStorage.delete(savedFilename);
      }
      catch (StorageException e2) {
        log.error("Failed to delete file after database transaction rollback. Orphan file created: {}",
            savedFilename, e2);
        throw new OrphanFileException(savedFilename);
      }
      throw e;
    }
    return new ResourceUploadResult(id, originalFilename, savedFilename, url);
  }

  public void cleanupOrphanResources() {
    Instant threshold = Instant.now().minus(24, ChronoUnit.HOURS);
    List<Resource> orphanResources = this.resourceRepository
        .findByStatusAndCreatedAtBefore(ResourceStatus.PENDING, threshold);
    int deletedCount = 0;
    for (Resource resource : orphanResources) {
      try {
        this.fileStorage.delete(resource.getSavedFilename());
        this.resourceRepository.delete(resource);
        deletedCount++;
        log.debug("Successfully deleted orphan resource: {}", resource.getSavedFilename());
      }
      catch (Exception e) {
        log.error("Failed to delete orphan resource: {}", resource.getSavedFilename(), e);
      }
    }
    if (deletedCount > 0) {
      log.info("Cleaned up {} orphan resources.", deletedCount);
    }
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
