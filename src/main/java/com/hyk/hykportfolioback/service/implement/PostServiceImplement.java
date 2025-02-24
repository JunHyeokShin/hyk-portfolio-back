package com.hyk.hykportfolioback.service.implement;

import com.hyk.hykportfolioback.dto.response.ResponseDto;
import com.hyk.hykportfolioback.dto.response.post.GetPostListResponseDto;
import com.hyk.hykportfolioback.entity.PostWithTagsEntity;
import com.hyk.hykportfolioback.repository.PostWithTagsRepository;
import com.hyk.hykportfolioback.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImplement implements PostService {

  @Value("${domain}")
  private String domain;

  private final PostWithTagsRepository postWithTagsRepository;

  @Override
  public ResponseEntity<? super GetPostListResponseDto> getPostList() {
    try {
      List<PostWithTagsEntity> postWithTagsEntities = new ArrayList<>();
      postWithTagsEntities = postWithTagsRepository.findAllByOrderByCreatedAtDesc();
      return GetPostListResponseDto.success(postWithTagsEntities);
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }
  }

}
