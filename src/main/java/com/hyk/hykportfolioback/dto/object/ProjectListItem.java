package com.hyk.hykportfolioback.dto.object;

import com.hyk.hykportfolioback.repository.resultSet.GetProjectListResultSet;
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

  public ProjectListItem(GetProjectListResultSet resultSet) {
    this.id = resultSet.getId();
    this.name = resultSet.getName();
    this.thumbnail = resultSet.getThumbnail();
    this.themeColor = resultSet.getThemeColor();
    this.description = resultSet.getDescription();
  }

  public static List<ProjectListItem> copyList(List<GetProjectListResultSet> resultSets) {
    List<ProjectListItem> list = new ArrayList<>();
    for (GetProjectListResultSet resultSet : resultSets) {
      ProjectListItem projectListItem = new ProjectListItem(resultSet);
      list.add(projectListItem);
    }
    return list;
  }

}
