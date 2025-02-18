package com.hyk.hykportfolioback.entity;

import com.hyk.hykportfolioback.dto.request.project.PostProjectRequestDto;
import com.hyk.hykportfolioback.dto.request.project.PutProjectRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

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
  private int commentCount;
  private int viewCount;
  private String createdAt;

  public ProjectEntity(PostProjectRequestDto dto, String thumbnail) {
    Date now = Date.from(Instant.now());
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String createdAt = simpleDateFormat.format(now);

    this.id = dto.getId();
    this.name = dto.getName();
    this.thumbnail = thumbnail;
    this.themeColor = dto.getThemeColor();
    this.description = dto.getDescription();
    this.content = dto.getContent();
    this.createdAt = createdAt;
  }

  public void increaseViewCount() {
    this.viewCount++;
  }

  public void updateProject(PutProjectRequestDto dto, String thumbnail) {
    this.name = dto.getName();
    this.thumbnail = thumbnail;
    this.themeColor = dto.getThemeColor();
    this.description = dto.getDescription();
    this.content = dto.getContent();
  }

}
