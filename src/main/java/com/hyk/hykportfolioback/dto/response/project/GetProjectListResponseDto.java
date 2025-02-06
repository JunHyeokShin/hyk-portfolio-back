package com.hyk.hykportfolioback.dto.response.project;

import com.hyk.hykportfolioback.common.ResponseCode;
import com.hyk.hykportfolioback.common.ResponseMessage;
import com.hyk.hykportfolioback.dto.object.ProjectListItem;
import com.hyk.hykportfolioback.dto.response.ResponseDto;
import com.hyk.hykportfolioback.entity.ProjectEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetProjectListResponseDto extends ResponseDto {

  private List<ProjectListItem> projectList;

  private GetProjectListResponseDto(List<ProjectEntity> projectEntities) {
    super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    this.projectList = ProjectListItem.copyList(projectEntities);
  }

  public static ResponseEntity<GetProjectListResponseDto> success(List<ProjectEntity> projectEntities) {
    GetProjectListResponseDto result = new GetProjectListResponseDto(projectEntities);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

}
