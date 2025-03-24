package com.hyk.hykportfolioback.dto.object;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import com.hyk.hykportfolioback.entity.PostWithTagsEntity;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PostListItem {

  private int id;
  private String title;
  private String thumbnail;
  private String themeColor;
  private List<String> tags;
  private int viewCount;
  private String createdAt;
  private String updatedAt;

  public PostListItem(PostWithTagsEntity postWithTagsEntity) {
    this.id = postWithTagsEntity.getId();
    this.title = postWithTagsEntity.getTitle();
    this.thumbnail = postWithTagsEntity.getThumbnail();
    this.themeColor = postWithTagsEntity.getThemeColor();
    this.viewCount = postWithTagsEntity.getViewCount();
    this.createdAt = postWithTagsEntity.getCreatedAt();
    this.updatedAt = postWithTagsEntity.getUpdatedAt();
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      this.tags = objectMapper.readValue(postWithTagsEntity.getTags(), new TypeReference<List<String>>() {
      });
    } catch (Exception exception) {
      exception.printStackTrace();
      throw new RuntimeJsonMappingException(exception.getMessage());
    }
  }

  public static List<PostListItem> copyList(List<PostWithTagsEntity> postWithTagsEntities) {
    List<PostListItem> list = new ArrayList<>();
    for (PostWithTagsEntity postWithTagsEntity : postWithTagsEntities) {
      PostListItem postListItem = new PostListItem(postWithTagsEntity);
      list.add(postListItem);
    }
    return list;
  }

}
