package com.hyk.hykportfolioback.dto.response.project;

import com.hyk.hykportfolioback.common.ResponseCode;
import com.hyk.hykportfolioback.common.ResponseMessage;
import com.hyk.hykportfolioback.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class PostProjectThumbnailResponseDto extends ResponseDto {

  private String thumbnail;

  private PostProjectThumbnailResponseDto(String url) {
    super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    this.thumbnail = url;
  }

  public static ResponseEntity<PostProjectThumbnailResponseDto> success(String url) {
    PostProjectThumbnailResponseDto result = new PostProjectThumbnailResponseDto(url);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

}
