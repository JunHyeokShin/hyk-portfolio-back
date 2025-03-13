package com.hyk.hykportfolioback.dto.response.post;

import com.hyk.hykportfolioback.common.ResponseCode;
import com.hyk.hykportfolioback.common.ResponseMessage;
import com.hyk.hykportfolioback.dto.object.PostListItem;
import com.hyk.hykportfolioback.dto.response.ResponseDto;
import com.hyk.hykportfolioback.entity.PostWithTagsEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetSearchPostListResponseDto extends ResponseDto {

  private List<PostListItem> searchList;

  private GetSearchPostListResponseDto(List<PostWithTagsEntity> postWithTagsEntities) {
    super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    this.searchList = PostListItem.copyList(postWithTagsEntities);
  }

  public static ResponseEntity<GetSearchPostListResponseDto> success(List<PostWithTagsEntity> postWithTagsEntities) {
    GetSearchPostListResponseDto result = new GetSearchPostListResponseDto(postWithTagsEntities);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

}
