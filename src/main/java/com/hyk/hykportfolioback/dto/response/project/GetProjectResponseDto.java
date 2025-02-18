package com.hyk.hykportfolioback.dto.response.project;

import com.hyk.hykportfolioback.common.ResponseCode;
import com.hyk.hykportfolioback.common.ResponseMessage;
import com.hyk.hykportfolioback.dto.object.ResourceListItem;
import com.hyk.hykportfolioback.dto.response.ResponseDto;
import com.hyk.hykportfolioback.entity.ProjectEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetProjectResponseDto extends ResponseDto {

  private String id;
  private String name;
  private String thumbnail;
  private String themeColor;
  private String description;
  private String content;
  private List<ResourceListItem> resourceList;

  private GetProjectResponseDto(ProjectEntity projectEntity, List<ResourceListItem> resourceList) {
    super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    this.id = projectEntity.getId();
    this.name = projectEntity.getName();
    this.thumbnail = projectEntity.getThumbnail();
    this.themeColor = projectEntity.getThemeColor();
    this.description = projectEntity.getDescription();
    this.content = projectEntity.getContent();
    this.resourceList = resourceList;
  }

  public static ResponseEntity<GetProjectResponseDto> success(ProjectEntity projectEntity,
                                                              List<ResourceListItem> resourceList) {
    GetProjectResponseDto result = new GetProjectResponseDto(projectEntity, resourceList);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  public static ResponseEntity<ResponseDto> notExistedProject() {
    ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_PROJECT, ResponseMessage.NOT_EXISTED_PROJECT);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
  }

}
