package com.hyk.hykportfolioback.dto.response.post;

import com.hyk.hykportfolioback.common.ResponseCode;
import com.hyk.hykportfolioback.common.ResponseMessage;
import com.hyk.hykportfolioback.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class PostPostResponseDto extends ResponseDto {

  private PostPostResponseDto() {
    super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
  }

  public static ResponseEntity<PostPostResponseDto> success() {
    PostPostResponseDto result = new PostPostResponseDto();
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

}
