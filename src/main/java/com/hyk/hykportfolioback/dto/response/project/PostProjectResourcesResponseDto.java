package com.hyk.hykportfolioback.dto.response.project;

import com.hyk.hykportfolioback.common.ResponseCode;
import com.hyk.hykportfolioback.common.ResponseMessage;
import com.hyk.hykportfolioback.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class PostProjectResourcesResponseDto extends ResponseDto {

  private final List<String> urls;

  private PostProjectResourcesResponseDto(List<String> urls) {
    super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    this.urls = urls;
  }

  public static ResponseEntity<PostProjectResourcesResponseDto> success(List<String> urls) {
    PostProjectResourcesResponseDto result = new PostProjectResourcesResponseDto(urls);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

}
