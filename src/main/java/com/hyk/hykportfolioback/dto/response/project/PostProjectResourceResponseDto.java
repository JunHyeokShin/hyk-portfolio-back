package com.hyk.hykportfolioback.dto.response.project;

import com.hyk.hykportfolioback.common.ResponseCode;
import com.hyk.hykportfolioback.common.ResponseMessage;
import com.hyk.hykportfolioback.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class PostProjectResourceResponseDto extends ResponseDto {

  private final String url;

  private PostProjectResourceResponseDto(String url) {
    super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    this.url = url;
  }

  public static ResponseEntity<PostProjectResourceResponseDto> success(String url) {
    PostProjectResourceResponseDto result = new PostProjectResourceResponseDto(url);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

}
