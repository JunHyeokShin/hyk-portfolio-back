package com.hyk.portfolio.project.application.port.in;

import java.time.Instant;

import com.hyk.portfolio.project.domain.model.Description;
import com.hyk.portfolio.project.domain.model.Slug;
import com.hyk.portfolio.project.domain.model.ThemeColor;
import com.hyk.portfolio.project.domain.model.Thumbnail;
import com.hyk.portfolio.project.domain.model.Title;

public record ProjectSummary(
    Slug slug,
    Title title,
    Thumbnail thumbnail,
    ThemeColor themeColor,
    Description description,
    Instant createdAt,
    Instant updatedAt
) {

}
