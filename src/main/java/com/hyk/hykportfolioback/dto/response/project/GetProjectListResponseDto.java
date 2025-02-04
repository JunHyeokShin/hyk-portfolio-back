package com.hyk.hykportfolioback.dto.response.project;

import com.hyk.hykportfolioback.common.ResponseCode;
import com.hyk.hykportfolioback.common.ResponseMessage;
import com.hyk.hykportfolioback.dto.object.ProjectListItem;
import com.hyk.hykportfolioback.dto.response.ResponseDto;
import com.hyk.hykportfolioback.repository.resultSet.GetProjectListResultSet;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetProjectListResponseDto extends ResponseDto {

  private List<ProjectListItem> projectList;

  private GetProjectListResponseDto(List<GetProjectListResultSet> resultSets) {
    super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    this.projectList = ProjectListItem.copyList(resultSets);
  }

  public static ResponseEntity<GetProjectListResponseDto> success(List<GetProjectListResultSet> resultSets) {
    GetProjectListResponseDto result = new GetProjectListResponseDto(resultSets);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

}
