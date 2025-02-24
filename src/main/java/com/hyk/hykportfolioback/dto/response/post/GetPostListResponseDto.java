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
public class GetPostListResponseDto extends ResponseDto {

  private List<PostListItem> postList;

  private GetPostListResponseDto(List<PostWithTagsEntity> postWithTagsEntities) {
    super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    this.postList = PostListItem.copyList(postWithTagsEntities);
  }

  public static ResponseEntity<GetPostListResponseDto> success(List<PostWithTagsEntity> postWithTagsEntities) {
    GetPostListResponseDto result = new GetPostListResponseDto(postWithTagsEntities);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

}
