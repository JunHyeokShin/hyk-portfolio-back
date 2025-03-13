package com.hyk.hykportfolioback.service;

import com.hyk.hykportfolioback.dto.request.post.PostPostRequestDto;
import com.hyk.hykportfolioback.dto.request.post.PutPostRequestDto;
import com.hyk.hykportfolioback.dto.response.post.*;
import org.springframework.http.ResponseEntity;

public interface PostService {

  ResponseEntity<? super GetPostListResponseDto> getPostList();

  ResponseEntity<? super GetPostResponseDto> getPost(Integer id);

  ResponseEntity<? super GetPostContentResponseDto> getPostContent(Integer id);

  ResponseEntity<? super GetNextIdResponseDto> getNextId();

  ResponseEntity<? super GetSearchPostListResponseDto> getSearchPostList(String searchWord);

  ResponseEntity<? super PostPostResponseDto> postPost(PostPostRequestDto dto);

  ResponseEntity<? super PutPostResponseDto> updatePost(Integer id, PutPostRequestDto dto);

  ResponseEntity<? super DeletePostResponseDto> deletePost(Integer id);

}
