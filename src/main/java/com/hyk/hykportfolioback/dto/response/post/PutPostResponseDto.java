package com.hyk.hykportfolioback.dto.response.post;

import com.hyk.hykportfolioback.common.ResponseCode;
import com.hyk.hykportfolioback.common.ResponseMessage;
import com.hyk.hykportfolioback.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class PutPostResponseDto extends ResponseDto {

  private PutPostResponseDto() {
    super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
  }

  public static ResponseEntity<PutPostResponseDto> success() {
    PutPostResponseDto result = new PutPostResponseDto();
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  public static ResponseEntity<ResponseDto> notExistedPost() {
    ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_POST, ResponseMessage.NOT_EXISTED_POST);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
  }

}
