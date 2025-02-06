package com.hyk.hykportfolioback.dto.response.project;

import com.hyk.hykportfolioback.common.ResponseCode;
import com.hyk.hykportfolioback.common.ResponseMessage;
import com.hyk.hykportfolioback.dto.response.ResponseDto;
import com.hyk.hykportfolioback.entity.ProjectEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class GetProjectContentResponseDto extends ResponseDto {

  private String id;
  private String content;

  private GetProjectContentResponseDto(ProjectEntity projectEntity) {
    super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    this.id = projectEntity.getId();
    this.content = projectEntity.getContent();
  }

  public static ResponseEntity<GetProjectContentResponseDto> success(ProjectEntity projectEntity) {
    GetProjectContentResponseDto result = new GetProjectContentResponseDto(projectEntity);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  public static ResponseEntity<ResponseDto> notExistedProject() {
    ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_PROJECT, ResponseMessage.NOT_EXISTED_PROJECT);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
  }

}
