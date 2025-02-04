package com.hyk.hykportfolioback.entity;

import com.hyk.hykportfolioback.dto.request.project.PostProjectRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "project")
@Table(name = "project")
public class ProjectEntity {

  @Id
  private String id;
  private String name;
  private String thumbnail;
  private String themeColor;
  private String description;
  private String content;
  private int comment_count;
  private int view_count;

  public ProjectEntity(PostProjectRequestDto dto) {
    this.id = dto.getId();
    this.name = dto.getName();
    this.thumbnail = dto.getThumbnail();
    this.themeColor = dto.getThemeColor();
    this.description = dto.getDescription();
    this.content = dto.getContent();
  }

}
