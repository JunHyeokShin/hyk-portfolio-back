package com.hyk.hykportfolioback.entity;

import com.hyk.hykportfolioback.dto.request.post.PostPostRequestDto;
import com.hyk.hykportfolioback.dto.request.post.PutPostRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "post")
@Table(name = "post")
public class PostEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String title;
  @Setter
  private String thumbnail;
  private String themeColor;
  private String content;
  private int commentCount;
  private int viewCount;
  private String createdAt;
  private String updatedAt;

  public PostEntity(PostPostRequestDto dto) {
    Date now = Date.from(Instant.now());
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String createdAt = simpleDateFormat.format(now);

    this.title = dto.getTitle();
    this.themeColor = dto.getThemeColor();
    this.content = dto.getContent();
    this.createdAt = createdAt;
    this.updatedAt = createdAt;
  }

  public void increaseViewCount() {
    this.viewCount++;
  }

  public void updatePost(PutPostRequestDto dto, String thumbnail) {
    Date now = Date.from(Instant.now());
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String updatedAt = simpleDateFormat.format(now);

    this.title = dto.getTitle();
    this.thumbnail = thumbnail;
    this.themeColor = dto.getThemeColor();
    this.content = dto.getContent();
    this.updatedAt = updatedAt;
  }

}
