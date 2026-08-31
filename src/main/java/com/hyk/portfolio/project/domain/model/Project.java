package com.hyk.portfolio.project.domain.model;

import java.time.Instant;

import lombok.Getter;

@Getter
public class Project {

  private final Long id;
  private Slug slug;
  private Title title;
  private Thumbnail thumbnail;
  private ThemeColor themeColor;
  private Description description;
  private Content content;
  private final Instant createdAt;
  private Instant updatedAt;

  private Project(
      Long id,
      Slug slug,
      Title title,
      Thumbnail thumbnail,
      ThemeColor themeColor,
      Description description,
      Content content,
      Instant createdAt,
      Instant updatedAt
  ) {
    this.id = id;
    this.slug = slug;
    this.title = title;
    this.thumbnail = thumbnail;
    this.themeColor = themeColor;
    this.description = description;
    this.content = content;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    validate();
  }

  public static Project write(
      Slug slug,
      Title title,
      Thumbnail thumbnail,
      ThemeColor themeColor,
      Description description,
      Content content
  ) {
    Instant now = Instant.now();
    return new Project(
        null, slug, title, thumbnail, themeColor, description, content, now, now);
  }

  public static Project reconstitute(
      Long id,
      Slug slug,
      Title title,
      Thumbnail thumbnail,
      ThemeColor themeColor,
      Description description,
      Content content,
      Instant createdAt,
      Instant updatedAt
  ) {
    if (id == null) {
      throw new IllegalArgumentException("id는 필수입니다");
    }
    return new Project(
        id, slug, title, thumbnail, themeColor, description, content, createdAt, updatedAt);
  }

  public void update(
      Slug slug,
      Title title,
      Thumbnail thumbnail,
      ThemeColor themeColor,
      Description description,
      Content content
  ) {
    this.slug = slug;
    this.title = title;
    this.thumbnail = thumbnail;
    this.themeColor = themeColor;
    this.description = description;
    this.content = content;
    this.updatedAt = Instant.now();
    validate();
  }

  private void validate() {
    if (this.slug == null) {
      throw new IllegalArgumentException("slug는 필수입니다");
    }
    if (this.title == null) {
      throw new IllegalArgumentException("title은 필수입니다");
    }
    if (this.content == null) {
      throw new IllegalArgumentException("content는 필수입니다");
    }
    if (this.createdAt == null) {
      throw new IllegalArgumentException("createdAt은 필수입니다");
    }
    if (this.updatedAt == null) {
      throw new IllegalArgumentException("updatedAt은 필수입니다");
    }
    if (this.updatedAt.isBefore(this.createdAt)) {
      throw new IllegalArgumentException("updatedAt은 createdAt보다 과거일 수 없습니다");
    }
  }

}
