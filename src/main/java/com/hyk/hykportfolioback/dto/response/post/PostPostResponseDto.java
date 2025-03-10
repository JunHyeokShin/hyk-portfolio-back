package com.hyk.hykportfolioback.dto.response.post;

import com.hyk.hykportfolioback.common.ResponseCode;
import com.hyk.hykportfolioback.common.ResponseMessage;
import com.hyk.hykportfolioback.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class PostPostResponseDto extends ResponseDto {

  private int id;

  private PostPostResponseDto(Integer id) {
    super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    this.id = id;
  }

  public static ResponseEntity<PostPostResponseDto> success(Integer id) {
    PostPostResponseDto result = new PostPostResponseDto(id);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

}
