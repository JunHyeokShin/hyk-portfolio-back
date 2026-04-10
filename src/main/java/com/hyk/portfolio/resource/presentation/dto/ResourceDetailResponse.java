package com.hyk.portfolio.resource.presentation.dto;

import java.time.Instant;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import com.hyk.portfolio.resource.application.dto.ResourceDetail;

public record ResourceDetailResponse(
    UUID id,
    String originalFilename,
    String savedFilename,
    String url,
    @JsonFormat(shape = Shape.NUMBER_INT) Instant createdAt
) {

  public static ResourceDetailResponse from(ResourceDetail resourceDetail) {
    return new ResourceDetailResponse(
        resourceDetail.id(),
        resourceDetail.originalFilename(),
        resourceDetail.savedFilename(),
        resourceDetail.url(),
        resourceDetail.createdAt()
    );
  }

}
