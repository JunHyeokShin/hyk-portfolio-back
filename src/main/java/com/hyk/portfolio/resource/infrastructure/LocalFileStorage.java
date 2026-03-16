package com.hyk.portfolio.resource.infrastructure;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.hyk.portfolio.resource.application.FileStorage;
import com.hyk.portfolio.resource.application.StorageException;

@ConditionalOnProperty(value = "storage.type", havingValue = "local", matchIfMissing = true)
@Component
public class LocalFileStorage implements FileStorage {

  private final Path uploadDirPath;
  private final String baseUri;

  public LocalFileStorage(
      @Value("${storage.local.upload-dir}") String uploadDir,
      @Value("${storage.local.base-uri}") String baseUri
  ) {
    this.uploadDirPath = Path.of(uploadDir).toAbsolutePath().normalize();
    this.baseUri = baseUri;
  }


  @Override
  public String upload(String filename, InputStream inputStream) {
    Path targetFilePath = getTargetFilePath(filename);
    try {
      if (!Files.exists(this.uploadDirPath)) {
        Files.createDirectories(this.uploadDirPath);
      }
      Files.copy(inputStream, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
    }
    catch (IOException e) {
      throw new StorageException("Failed to store file: " + filename, e);
    }
    return UriComponentsBuilder.fromUriString(this.baseUri)
        .pathSegment("resources", filename)
        .toUriString();
  }

  @Override
  public void delete(String filename) {
    Path targetFilePath = getTargetFilePath(filename);
    try {
      Files.deleteIfExists(targetFilePath);
    }
    catch (IOException e) {
      throw new StorageException("Failed to delete file: " + filename, e);
    }
  }

  private Path getTargetFilePath(String filename) {
    Path targetFilePath = this.uploadDirPath.resolve(filename).toAbsolutePath().normalize();
    if (!targetFilePath.startsWith(this.uploadDirPath)) {
      throw new StorageException("Path traversal attempt detected: " + filename);
    }
    return targetFilePath;
  }

}
