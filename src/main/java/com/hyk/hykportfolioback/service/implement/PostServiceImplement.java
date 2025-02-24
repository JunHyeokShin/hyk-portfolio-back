package com.hyk.hykportfolioback.service.implement;

import com.hyk.hykportfolioback.dto.response.ResponseDto;
import com.hyk.hykportfolioback.dto.response.post.GetPostContentResponseDto;
import com.hyk.hykportfolioback.dto.response.post.GetPostListResponseDto;
import com.hyk.hykportfolioback.entity.PostEntity;
import com.hyk.hykportfolioback.entity.PostWithTagsEntity;
import com.hyk.hykportfolioback.repository.PostRepository;
import com.hyk.hykportfolioback.repository.PostWithTagsRepository;
import com.hyk.hykportfolioback.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImplement implements PostService {

  @Value("${domain}")
  private String domain;

  private final PostRepository postRepository;
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

  @Override
  public ResponseEntity<? super GetPostContentResponseDto> getPostContent(Integer id) {
    PostWithTagsEntity postWithTagsEntity = null;

    try {
      Optional<PostEntity> optionalPostEntity = postRepository.findById(id);
      if (optionalPostEntity.isEmpty()) return GetPostContentResponseDto.notExistedPost();
      PostEntity postEntity = optionalPostEntity.get();
      postEntity.increaseViewCount();
      postRepository.save(postEntity);

      Optional<PostWithTagsEntity> optionalPostWithTagsEntity = postWithTagsRepository.findById(id);
      if (optionalPostWithTagsEntity.isEmpty()) return GetPostContentResponseDto.notExistedPost();
      postWithTagsEntity = optionalPostWithTagsEntity.get();
      System.out.println(postWithTagsEntity.getViewCount());
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetPostContentResponseDto.success(postWithTagsEntity);
  }

}
