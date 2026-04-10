package com.hyk.portfolio.resource.application.dto;

import java.time.Instant;
import java.util.UUID;

import com.hyk.portfolio.resource.domain.Resource;

public record ResourceDetail(
    UUID id,
    String originalFilename,
    String savedFilename,
    String url,
    Instant createdAt
) {

  public static ResourceDetail from(Resource resource) {
    return new ResourceDetail(
        resource.getId(),
        resource.getOriginalFilename(),
        resource.getSavedFilename(),
        resource.getUrl(),
        resource.getCreatedAt()
    );
  }

}
