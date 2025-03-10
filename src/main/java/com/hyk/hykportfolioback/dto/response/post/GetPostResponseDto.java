package com.hyk.hykportfolioback.dto.response.post;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import com.hyk.hykportfolioback.common.ResponseCode;
import com.hyk.hykportfolioback.common.ResponseMessage;
import com.hyk.hykportfolioback.dto.object.ResourceListItem;
import com.hyk.hykportfolioback.dto.response.ResponseDto;
import com.hyk.hykportfolioback.entity.PostWithTagsEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetPostResponseDto extends ResponseDto {

  private int id;
  private String title;
  private String thumbnail;
  private String themeColor;
  private List<String> tags;
  private String content;
  private List<ResourceListItem> resourceList;

  private GetPostResponseDto(PostWithTagsEntity postWithTagsEntity, List<ResourceListItem> resourceList) {
    super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    this.id = postWithTagsEntity.getId();
    this.title = postWithTagsEntity.getTitle();
    this.thumbnail = postWithTagsEntity.getThumbnail();
    this.themeColor = postWithTagsEntity.getThemeColor();
    this.content = postWithTagsEntity.getContent();
    this.resourceList = resourceList;
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      this.tags = objectMapper.readValue(postWithTagsEntity.getTags(), new TypeReference<List<String>>() {
      });
    } catch (Exception exception) {
      exception.printStackTrace();
      throw new RuntimeJsonMappingException(exception.getMessage());
    }
  }

  public static ResponseEntity<GetPostResponseDto> success(PostWithTagsEntity postWithTagsEntity,
                                                           List<ResourceListItem> resourceList) {
    GetPostResponseDto result = new GetPostResponseDto(postWithTagsEntity, resourceList);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  public static ResponseEntity<ResponseDto> notExistedPost() {
    ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_POST, ResponseMessage.NOT_EXISTED_POST);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
  }

}
