package com.hyk.portfolio.project.adapter.out.persistence;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.hyk.portfolio.project.application.port.in.ProjectSummary;
import com.hyk.portfolio.project.domain.model.Content;
import com.hyk.portfolio.project.domain.model.Description;
import com.hyk.portfolio.project.domain.model.Project;
import com.hyk.portfolio.project.domain.model.Slug;
import com.hyk.portfolio.project.domain.model.ThemeColor;
import com.hyk.portfolio.project.domain.model.Thumbnail;
import com.hyk.portfolio.project.domain.model.Title;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class ProjectMapper {

  static ProjectJpaEntity toEntity(Project project) {
    return ProjectJpaEntity.builder()
        .id(project.getId())
        .slug(project.getSlug().value())
        .title(project.getTitle().value())
        .thumbnail(project.getThumbnail() != null ? project.getThumbnail().url() : null)
        .themeColor(project.getThemeColor() != null ? project.getThemeColor().code() : null)
        .description(project.getDescription() != null ? project.getDescription().value() : null)
        .content(project.getContent().value())
        .createdAt(project.getCreatedAt())
        .updatedAt(project.getUpdatedAt())
        .build();
  }

  static Project toDomain(ProjectJpaEntity entity) {
    return Project.reconstitute(
        entity.getId(),
        Slug.of(entity.getSlug()),
        Title.of(entity.getTitle()),
        entity.getThumbnail() != null ? Thumbnail.of(entity.getThumbnail()) : null,
        entity.getThemeColor() != null ? ThemeColor.of(entity.getThemeColor()) : null,
        entity.getDescription() != null ? Description.of(entity.getDescription()) : null,
        Content.of(entity.getContent()),
        entity.getCreatedAt(),
        entity.getUpdatedAt()
    );
  }

  static ProjectSummary toSummary(ProjectSummaryView view) {
    return new ProjectSummary(
        Slug.of(view.slug()),
        Title.of(view.title()),
        view.thumbnail() != null ? Thumbnail.of(view.thumbnail()) : null,
        view.themeColor() != null ? ThemeColor.of(view.themeColor()) : null,
        view.description() != null ? Description.of(view.description()) : null,
        view.createdAt(),
        view.updatedAt()
    );
  }

  static void updateEntity(ProjectJpaEntity entity, Project project) {
    entity.update(
        project.getSlug().value(),
        project.getTitle().value(),
        project.getThumbnail() != null ? project.getThumbnail().url() : null,
        project.getThemeColor() != null ? project.getThemeColor().code() : null,
        project.getDescription() != null ? project.getDescription().value() : null,
        project.getContent().value(),
        project.getUpdatedAt()
    );
  }

}
