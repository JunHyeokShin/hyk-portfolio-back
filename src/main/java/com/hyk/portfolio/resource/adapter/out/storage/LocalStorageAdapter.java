package com.hyk.portfolio.resource.adapter.out.storage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.hyk.portfolio.resource.application.port.out.DeleteFilePort;
import com.hyk.portfolio.resource.application.port.out.StorageException;
import com.hyk.portfolio.resource.application.port.out.StoreFilePort;

@ConditionalOnProperty(name = "resource.storage.mode", havingValue = "local")
@EnableConfigurationProperties(LocalStorageProperties.class)
@Component
class LocalStorageAdapter implements StoreFilePort, DeleteFilePort {

  private final Path rootDir;
  private final String baseUri;

  LocalStorageAdapter(LocalStorageProperties properties) {
    this.rootDir = properties.rootDir();
    this.baseUri = properties.baseUri();
  }

  @Override
  public String store(String filename, InputStream content) {
    Path destination = getDestination(filename);
    try {
      Files.createDirectories(this.rootDir);
      Files.copy(content, destination);
    }
    catch (FileAlreadyExistsException e) {
      throw new StorageException("이미 존재하는 파일명입니다: " + filename, e);
    }
    catch (IOException e) {
      deleteQuietly(destination);
      throw new StorageException("파일 저장에 실패했습니다: " + filename, e);
    }
    return UriComponentsBuilder.fromUriString(this.baseUri)
        .pathSegment(filename).toUriString();
  }

  @Override
  public void delete(String filename) {
    try {
      Files.deleteIfExists(getDestination(filename));
    }
    catch (IOException e) {
      throw new StorageException("파일 삭제에 실패했습니다: " + filename, e);
    }
  }

  private Path getDestination(String filename) {
    Path destination = this.rootDir.resolve(filename).normalize();
    if (!destination.getParent().equals(this.rootDir)) {
      throw new IllegalArgumentException("파일명에 경로 문자를 포함할 수 없습니다: " + filename);
    }
    return destination;
  }

  private void deleteQuietly(Path path) {
    try {
      Files.deleteIfExists(path);
    }
    catch (IOException ignored) {
    }
  }

}
