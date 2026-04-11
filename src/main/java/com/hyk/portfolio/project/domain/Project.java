package com.hyk.portfolio.project.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.hyk.portfolio.common.domain.BaseTimeEntity;
import com.hyk.portfolio.common.domain.ThemeColor;
import com.hyk.portfolio.common.domain.Thumbnail;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "project")
@Entity
public class Project extends BaseTimeEntity {

  @EmbeddedId
  private ProjectId id;

  @Column(name = "title", nullable = false)
  private String title;

  @Embedded
  private Thumbnail thumbnail;

  @Embedded
  private ThemeColor themeColor;

  @Column(name = "description")
  private String description;

  @Column(name = "content")
  private String content;

  @Column(name = "view_count", nullable = false)
  private int viewCount = 0;

  private Project(ProjectId id, String title, Thumbnail thumbnail,
      ThemeColor themeColor, String description, String content) {
    this.id = id;
    this.title = title;
    this.thumbnail = thumbnail;
    this.themeColor = themeColor;
    this.description = description;
    this.content = content;
  }

  public static Project create(ProjectId id, String title, Thumbnail thumbnail,
      ThemeColor themeColor, String description, String content) {
    return new Project(id, title, thumbnail, themeColor, description, content);
  }

  public void update(String title, Thumbnail thumbnail, ThemeColor themeColor,
      String description, String content) {
    this.title = title;
    this.thumbnail = thumbnail;
    this.themeColor = themeColor;
    this.description = description;
    this.content = content;
  }

  public void increaseViewCount() {
    this.viewCount++;
  }

}
