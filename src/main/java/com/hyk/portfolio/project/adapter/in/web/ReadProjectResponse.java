package com.hyk.portfolio.project.adapter.in.web;

import java.time.Instant;

import com.hyk.portfolio.project.domain.model.Project;

record ReadProjectResponse(
    String slug,
    String title,
    String thumbnail,
    String themeColor,
    String description,
    String content,
    Instant createdAt,
    Instant updatedAt
) {

  static ReadProjectResponse from(Project project) {
    return new ReadProjectResponse(
        project.getSlug().value(),
        project.getTitle().value(),
        project.getThumbnail() != null ? project.getThumbnail().url() : null,
        project.getThemeColor() != null ? project.getThemeColor().code() : null,
        project.getDescription() != null ? project.getDescription().value() : null,
        project.getContent().value(),
        project.getCreatedAt(),
        project.getUpdatedAt()
    );
  }

}
