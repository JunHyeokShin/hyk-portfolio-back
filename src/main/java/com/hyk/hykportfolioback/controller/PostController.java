package com.hyk.hykportfolioback.controller;

import com.hyk.hykportfolioback.dto.response.post.GetPostContentResponseDto;
import com.hyk.hykportfolioback.dto.response.post.GetPostListResponseDto;
import com.hyk.hykportfolioback.dto.response.post.GetPostResponseDto;
import com.hyk.hykportfolioback.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {

  private final PostService postService;

  @GetMapping("")
  public ResponseEntity<? super GetPostListResponseDto> getPostList() {
    ResponseEntity<? super GetPostListResponseDto> response = postService.getPostList();
    return response;
  }

  @GetMapping("/{id}")
  public ResponseEntity<? super GetPostResponseDto> getPost(@PathVariable("id") Integer id) {
    ResponseEntity<? super GetPostResponseDto> response = postService.getPost(id);
    return response;
  }

  @GetMapping("/{id}/content")
  public ResponseEntity<? super GetPostContentResponseDto> getPostContent(@PathVariable("id") Integer id) {
    ResponseEntity<? super GetPostContentResponseDto> response = postService.getPostContent(id);
    return response;
  }

}
