package com.hyk.portfolio.resource.adapter.out.storage;

import java.nio.file.Path;
import java.time.Duration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ConditionalOnProperty(name = "resource.storage.mode", havingValue = "local")
@EnableConfigurationProperties(LocalStorageProperties.class)
@Configuration
class LocalStorageWebConfig implements WebMvcConfigurer {

  private final String location;

  LocalStorageWebConfig(LocalStorageProperties properties) {
    this.location = toLocation(properties.rootDir());
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/resources/**")
        .addResourceLocations(this.location)
        .setCacheControl(CacheControl.maxAge(Duration.ofDays(365)));
  }

  private String toLocation(Path rootDir) {
    String location = rootDir.toUri().toString();
    return location.endsWith("/") ? location : location + "/";
  }

}
