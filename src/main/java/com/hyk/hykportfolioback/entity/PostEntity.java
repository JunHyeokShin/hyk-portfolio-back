package com.hyk.hykportfolioback.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
  private String thumbnail;
  private String themeColor;
  private String content;
  private int commentCount;
  private int viewCount;
  private String createdAt;
  private String updatedAt;

  public void increaseViewCount() {
    this.viewCount++;
  }

}
