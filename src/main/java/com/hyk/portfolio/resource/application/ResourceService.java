package com.hyk.portfolio.resource.application;

import java.io.InputStream;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.portfolio.resource.application.exception.OrphanFileException;
import com.hyk.portfolio.resource.application.exception.StorageException;
import com.hyk.portfolio.resource.domain.Resource;
import com.hyk.portfolio.resource.domain.ResourceRepository;

@RequiredArgsConstructor
@Transactional
@Service
public class ResourceService {

  private final ResourceRepository resourceRepository;
  private final FileStorage fileStorage;

  public ResourceUploadResult uploadResource(String originalFilename, InputStream inputStream) {
    UUID id = UUID.randomUUID();
    String extension = getExtension(originalFilename);
    String savedFilename = id + extension;
    Resource resource = Resource.builder()
        .id(id)
        .originalFilename(originalFilename)
        .savedFilename(savedFilename)
        .build();

    this.fileStorage.upload(savedFilename, inputStream);
    try {
      this.resourceRepository.save(resource);
    }
    catch (RuntimeException e) {
      try {
        this.fileStorage.delete(savedFilename);
      }
      catch (StorageException e2) {
        throw new OrphanFileException(
            "Failed to delete file after database transaction rollback. Orphan file created: ",
            savedFilename,
            e
        );
      }
      throw e;
    }

    String url = this.fileStorage.getUrl(savedFilename);
    return new ResourceUploadResult(id, savedFilename, url);
  }

  public String getExtension(String filename) {
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
