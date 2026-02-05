package com.hyk.portfolio.project.infrastructure;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.hyk.portfolio.common.entity.BaseTimeEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "project")
@Entity
public class ProjectJpaEntity extends BaseTimeEntity {

  @Column(name = "id", nullable = false)
  @Id
  private String id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "thumbnail", length = 2048)
  private String thumbnail;

  @Column(name = "theme_color", length = 7)
  private String themeColor;

  @Column(name = "description")
  private String description;

  @Column(name = "content")
  @Lob
  private String content;

  @Column(name = "view_count", nullable = false)
  private Integer viewCount = 0;

}
