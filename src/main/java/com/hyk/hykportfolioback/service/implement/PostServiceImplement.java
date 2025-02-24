package com.hyk.hykportfolioback.service.implement;

import com.hyk.hykportfolioback.dto.object.ResourceListItem;
import com.hyk.hykportfolioback.dto.response.ResponseDto;
import com.hyk.hykportfolioback.dto.response.post.GetPostContentResponseDto;
import com.hyk.hykportfolioback.dto.response.post.GetPostListResponseDto;
import com.hyk.hykportfolioback.dto.response.post.GetPostResponseDto;
import com.hyk.hykportfolioback.entity.PostEntity;
import com.hyk.hykportfolioback.entity.PostWithTagsEntity;
import com.hyk.hykportfolioback.repository.PostRepository;
import com.hyk.hykportfolioback.repository.PostWithTagsRepository;
import com.hyk.hykportfolioback.service.PostService;
import com.hyk.hykportfolioback.util.ResourceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
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
    List<PostWithTagsEntity> postWithTagsEntities = new ArrayList<>();

    try {
      postWithTagsEntities = postWithTagsRepository.findAllByOrderByCreatedAtDesc();
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetPostListResponseDto.success(postWithTagsEntities);
  }

  @Override
  public ResponseEntity<? super GetPostResponseDto> getPost(Integer id) {
    PostWithTagsEntity postWithTagsEntity = null;
    List<ResourceListItem> resourceList = new ArrayList<>();

    try {
      Optional<PostWithTagsEntity> optionalPostWithTagsEntity = postWithTagsRepository.findById(id);
      if (optionalPostWithTagsEntity.isEmpty()) return GetPostResponseDto.notExistedPost();
      postWithTagsEntity = optionalPostWithTagsEntity.get();

      File directory = new File(System.getProperty("user.dir") + "/resources/post/" + id);
      File[] resources = directory.listFiles();

      if (resources == null) return GetPostResponseDto.success(postWithTagsEntity, resourceList);

      for (File resource : resources) {
        if (!ResourceUtils.isThumbnailFile(resource)) {
          String fileName = resource.getName();
          String url = domain + "/resources/post/" + id + "/" + fileName;
          ResourceListItem resourceListItem = new ResourceListItem(fileName, url);
          resourceList.add(resourceListItem);
        }
      }
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetPostResponseDto.success(postWithTagsEntity, resourceList);
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
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetPostContentResponseDto.success(postWithTagsEntity);
  }

}
