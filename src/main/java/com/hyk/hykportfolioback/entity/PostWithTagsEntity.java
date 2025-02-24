package com.hyk.hykportfolioback.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "post_with_tags")
@Table(name = "post_with_tags")
public class PostWithTagsEntity {

  @Id
  private int id;
  private String title;
  private String thumbnail;
  private String themeColor;
  private String content;
  private String tags;
  private int commentCount;
  private int viewCount;
  private String createdAt;
  private String updatedAt;

}
