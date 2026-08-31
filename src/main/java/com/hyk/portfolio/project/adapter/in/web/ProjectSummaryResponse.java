package com.hyk.portfolio.project.adapter.in.web;

import java.time.Instant;

import com.hyk.portfolio.project.application.port.in.ProjectSummary;

record ProjectSummaryResponse(
    String slug,
    String title,
    String thumbnail,
    String themeColor,
    String description,
    Instant createdAt,
    Instant updatedAt
) {

  static ProjectSummaryResponse from(ProjectSummary summary) {
    return new ProjectSummaryResponse(
        summary.slug().value(),
        summary.title().value(),
        summary.thumbnail() != null ? summary.thumbnail().url() : null,
        summary.themeColor() != null ? summary.themeColor().code() : null,
        summary.description() != null ? summary.description().value() : null,
        summary.createdAt(),
        summary.updatedAt()
    );
  }

}
