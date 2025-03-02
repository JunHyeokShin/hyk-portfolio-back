package com.hyk.hykportfolioback.controller;

import com.hyk.hykportfolioback.dto.request.post.PostPostRequestDto;
import com.hyk.hykportfolioback.dto.request.post.PutPostRequestDto;
import com.hyk.hykportfolioback.dto.response.post.*;
import com.hyk.hykportfolioback.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

  @GetMapping("/next-id")
  public ResponseEntity<? super GetNextIdResponseDto> getNextId() {
    ResponseEntity<? super GetNextIdResponseDto> response = postService.getNextId();
    return response;
  }

  @PostMapping("")
  public ResponseEntity<? super PostPostResponseDto> postPost(@ModelAttribute @Valid PostPostRequestDto request) {
    ResponseEntity<? super PostPostResponseDto> response = postService.postPost(request);
    return response;
  }

  @PutMapping("/{id}")
  public ResponseEntity<? super PutPostResponseDto> putPost(@PathVariable("id") Integer id,
                                                            @ModelAttribute @Valid PutPostRequestDto request) {
    ResponseEntity<? super PutPostResponseDto> response = postService.updatePost(id, request);
    return response;
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<? super DeletePostResponseDto> deletePost(@PathVariable("id") Integer id) {
    ResponseEntity<? super DeletePostResponseDto> response = postService.deletePost(id);
    return response;
  }

}
