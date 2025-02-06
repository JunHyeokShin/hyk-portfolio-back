package com.hyk.hykportfolioback.dto.object;

import com.hyk.hykportfolioback.entity.ProjectEntity;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ProjectListItem {

  private String id;
  private String name;
  private String thumbnail;
  private String themeColor;
  private String description;

  public ProjectListItem(ProjectEntity projectEntity) {
    this.id = projectEntity.getId();
    this.name = projectEntity.getName();
    this.thumbnail = projectEntity.getThumbnail();
    this.themeColor = projectEntity.getThemeColor();
    this.description = projectEntity.getDescription();
  }

  public static List<ProjectListItem> copyList(List<ProjectEntity> projectEntities) {
    List<ProjectListItem> list = new ArrayList<>();
    for (ProjectEntity projectEntity : projectEntities) {
      ProjectListItem projectListItem = new ProjectListItem(projectEntity);
      list.add(projectListItem);
    }
    return list;
  }

}
