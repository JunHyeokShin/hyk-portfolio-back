package com.hyk.hykportfolioback.dto.response.post;

import com.hyk.hykportfolioback.common.ResponseCode;
import com.hyk.hykportfolioback.common.ResponseMessage;
import com.hyk.hykportfolioback.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class GetNextIdResponseDto extends ResponseDto {

  private int id;

  private GetNextIdResponseDto(Integer id) {
    super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    this.id = id;
  }

  public static ResponseEntity<GetNextIdResponseDto> success(Integer id) {
    GetNextIdResponseDto result = new GetNextIdResponseDto(id);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

}
