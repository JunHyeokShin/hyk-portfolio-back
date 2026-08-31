package com.hyk.portfolio.project.adapter.out.persistence;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.hyk.portfolio.project.domain.model.Description;
import com.hyk.portfolio.project.domain.model.Slug;
import com.hyk.portfolio.project.domain.model.ThemeColor;
import com.hyk.portfolio.project.domain.model.Thumbnail;
import com.hyk.portfolio.project.domain.model.Title;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(
    name = "projects",
    indexes = @Index(name = "idx_projects_created_at", columnList = "created_at DESC")
)
@Entity
class ProjectJpaEntity {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long id;

  @Column(nullable = false, unique = true, length = Slug.MAX_LENGTH)
  private String slug;

  @Column(nullable = false, length = Title.MAX_LENGTH)
  private String title;

  @Column(length = Thumbnail.MAX_LENGTH)
  private String thumbnail;

  @Column(length = ThemeColor.LENGTH)
  private String themeColor;

  @Column(length = Description.MAX_LENGTH)
  private String description;

  @Column(nullable = false, columnDefinition = "LONGTEXT")
  private String content;

  @Column(nullable = false, updatable = false)
  private Instant createdAt;

  @Column(nullable = false)
  private Instant updatedAt;

  void update(
      String slug,
      String title,
      String thumbnail,
      String themeColor,
      String description,
      String content,
      Instant updatedAt
  ) {
    this.slug = slug;
    this.title = title;
    this.thumbnail = thumbnail;
    this.themeColor = themeColor;
    this.description = description;
    this.content = content;
    this.updatedAt = updatedAt;
  }

}
