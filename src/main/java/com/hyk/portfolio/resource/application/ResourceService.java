package com.hyk.portfolio.resource.application;

import java.io.InputStream;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.portfolio.resource.domain.Resource;
import com.hyk.portfolio.resource.domain.ResourceRepository;
import com.hyk.portfolio.resource.exception.OrphanFileException;
import com.hyk.portfolio.resource.exception.StorageException;

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
        throw new OrphanFileException(savedFilename);
      }
      throw e;
    }
    return new ResourceUploadResult(id, originalFilename, savedFilename, url);
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
