package com.hyk.hykportfolioback.dto.response.post;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import com.hyk.hykportfolioback.common.ResponseCode;
import com.hyk.hykportfolioback.common.ResponseMessage;
import com.hyk.hykportfolioback.dto.response.ResponseDto;
import com.hyk.hykportfolioback.entity.PostWithTagsEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetPostContentResponseDto extends ResponseDto {

  private int id;
  private List<String> tags;
  private String content;

  private GetPostContentResponseDto(PostWithTagsEntity postWithTagsEntity) {
    super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);

    this.id = postWithTagsEntity.getId();
    this.content = postWithTagsEntity.getContent();
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      this.tags = objectMapper.readValue(postWithTagsEntity.getTags(), new TypeReference<List<String>>() {
      });
    } catch (Exception exception) {
      exception.printStackTrace();
      throw new RuntimeJsonMappingException(exception.getMessage());
    }
  }

  public static ResponseEntity<GetPostContentResponseDto> success(PostWithTagsEntity postWithTagsEntity) {
    GetPostContentResponseDto result = new GetPostContentResponseDto(postWithTagsEntity);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  public static ResponseEntity<ResponseDto> notExistedPost() {
    ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_POST, ResponseMessage.NOT_EXISTED_POST);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
  }

}
