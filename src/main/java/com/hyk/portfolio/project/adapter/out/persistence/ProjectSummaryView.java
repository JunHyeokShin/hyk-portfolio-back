package com.hyk.portfolio.project.adapter.out.persistence;

import java.time.Instant;

record ProjectSummaryView(
    String slug,
    String title,
    String thumbnail,
    String themeColor,
    String description,
    Instant createdAt,
    Instant updatedAt
) {

}
