package com.hyk.portfolio.resource.adapter.out.storage;

import java.nio.file.Path;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "resource.storage.local")
record LocalStorageProperties(
    @NotNull Path rootDir,
    @NotBlank String baseUri
) {

  LocalStorageProperties {
    rootDir = rootDir.toAbsolutePath().normalize();
  }

}
