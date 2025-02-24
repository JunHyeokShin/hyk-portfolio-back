package com.hyk.hykportfolioback.service;

import com.hyk.hykportfolioback.dto.response.post.GetPostContentResponseDto;
import com.hyk.hykportfolioback.dto.response.post.GetPostListResponseDto;
import org.springframework.http.ResponseEntity;

public interface PostService {

  ResponseEntity<? super GetPostListResponseDto> getPostList();

  ResponseEntity<? super GetPostContentResponseDto> getPostContent(Integer id);

}
