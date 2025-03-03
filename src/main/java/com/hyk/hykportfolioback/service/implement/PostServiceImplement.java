package com.hyk.hykportfolioback.service.implement;

import com.hyk.hykportfolioback.dto.object.ResourceListItem;
import com.hyk.hykportfolioback.dto.request.post.PostPostRequestDto;
import com.hyk.hykportfolioback.dto.request.post.PutPostRequestDto;
import com.hyk.hykportfolioback.dto.response.ResponseDto;
import com.hyk.hykportfolioback.dto.response.post.*;
import com.hyk.hykportfolioback.entity.PostEntity;
import com.hyk.hykportfolioback.entity.PostTagEntity;
import com.hyk.hykportfolioback.entity.PostWithTagsEntity;
import com.hyk.hykportfolioback.entity.TagEntity;
import com.hyk.hykportfolioback.repository.PostRepository;
import com.hyk.hykportfolioback.repository.PostTagRepository;
import com.hyk.hykportfolioback.repository.PostWithTagsRepository;
import com.hyk.hykportfolioback.repository.TagRepository;
import com.hyk.hykportfolioback.service.PostService;
import com.hyk.hykportfolioback.util.ResourceUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImplement implements PostService {

  @Value("${domain}")
  private String domain;

  @PersistenceContext
  private EntityManager entityManager;

  private final PostRepository postRepository;
  private final TagRepository tagRepository;
  private final PostTagRepository postTagRepository;
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

  @Override
  public ResponseEntity<? super GetNextIdResponseDto> getNextId() {
    Integer id = null;

    try {
      entityManager.createNativeQuery("ANALYZE TABLE post").getResultList();
      id = postRepository.getNextAutoIncrementValue();
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetNextIdResponseDto.success(id);
  }

  @Override
  @Transactional
  public ResponseEntity<? super PostPostResponseDto> postPost(PostPostRequestDto dto) {
    Integer id = null;
    String thumbnail = null;
    MultipartFile thumbnailFile = dto.getThumbnailFile();
    List<MultipartFile> resourceFiles = dto.getResourceFiles();
    List<String> tags = dto.getTags();

    try {
      PostEntity postEntity = new PostEntity(dto);
      postRepository.save(postEntity);
      id = postEntity.getId();

      if (thumbnailFile != null) {
        if (thumbnailFile.isEmpty()) return ResponseDto.emptyFile();

        String savePathString = ResourceUtils.createPostThumbnailSavePath(id, thumbnailFile);
        Path savePath = Paths.get(savePathString);
        ResourceUtils.saveFile(thumbnailFile, savePath);
        thumbnail = domain + "/resources/post/" + id + "/thumbnail" + ResourceUtils.getFileExtension(thumbnailFile);

        postEntity.setThumbnail(thumbnail);
        postRepository.save(postEntity);
      }

      if (resourceFiles != null) {
        for (MultipartFile resourceFile : resourceFiles) {
          if (resourceFile.isEmpty()) {
            ResourceUtils.deletePostDirectory(id);
            return ResponseDto.emptyFile();
          }

          String savePathString = ResourceUtils.createPostResourceSavePath(id, resourceFile);
          Path savePath = Paths.get(savePathString);
          ResourceUtils.saveFile(resourceFile, savePath);
        }
      }

      if (tags != null) {
        int idx = 0;
        for (String tag : tags) {
          TagEntity tagEntity = tagRepository.findByName(tag);
          if (tagEntity == null) {
            tagEntity = new TagEntity(tag, postEntity.getCreatedAt());
            tagRepository.save(tagEntity);
          }

          PostTagEntity postTagEntity = new PostTagEntity(id, tagEntity.getId(), idx);
          postTagRepository.save(postTagEntity);
          idx++;
        }
      }
    } catch (IOException exception) {
      exception.printStackTrace();
      if (id != null) ResourceUtils.deletePostDirectory(id);
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      return ResponseDto.fileSaveError();
    } catch (Exception exception) {
      exception.printStackTrace();
      if (id != null) ResourceUtils.deletePostDirectory(id);
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      return ResponseDto.databaseError();
    }

    return PostPostResponseDto.success(id);
  }

  @Override
  @Transactional
  public ResponseEntity<? super PutPostResponseDto> updatePost(Integer id, PutPostRequestDto dto) {
    String thumbnail = null;
    MultipartFile thumbnailFile = dto.getThumbnailFile();
    List<MultipartFile> resourceFiles = dto.getResourceFiles();
    List<String> tags = dto.getTags();
    File directory = new File(System.getProperty("user.dir") + "/resources/post/" + id);
    File tempDirectory = new File(System.getProperty("user.dir") + "/resources/post/" + id + "_temp");
    Optional<PostEntity> optionalPostEntity;

    try {
      directory.renameTo(tempDirectory);

      optionalPostEntity = postRepository.findById(id);
      if (optionalPostEntity.isEmpty()) {
        tempDirectory.renameTo(directory);
        return PutPostResponseDto.notExistedPost();
      }

      if (thumbnailFile != null) {
        if (thumbnailFile.isEmpty()) {
          tempDirectory.renameTo(directory);
          return ResponseDto.emptyFile();
        }

        String savePathString = ResourceUtils.createPostThumbnailSavePath(id, thumbnailFile);
        Path savePath = Paths.get(savePathString);
        ResourceUtils.saveFile(thumbnailFile, savePath);
        thumbnail = domain + "/resources/post/" + id + "/thumbnail" + ResourceUtils.getFileExtension(thumbnailFile);
      }

      if (resourceFiles != null) {
        for (MultipartFile resourceFile : resourceFiles) {
          if (resourceFile.isEmpty()) {
            ResourceUtils.deletePostDirectory(id);
            tempDirectory.renameTo(directory);
            return ResponseDto.emptyFile();
          }

          String savePathString = ResourceUtils.createPostResourceSavePath(id, resourceFile);
          Path savePath = Paths.get(savePathString);
          ResourceUtils.saveFile(resourceFile, savePath);
        }
      }

      PostEntity postEntity = optionalPostEntity.get();
      postEntity.updatePost(dto, thumbnail);
      postRepository.save(postEntity);

      postTagRepository.deleteAllByPostId(id);
      if (tags != null) {
        int idx = 0;
        for (String tag : tags) {
          TagEntity tagEntity = tagRepository.findByName(tag);
          if (tagEntity == null) {
            tagEntity = new TagEntity(tag, postEntity.getUpdatedAt());
            tagRepository.save(tagEntity);
          }

          PostTagEntity postTagEntity = new PostTagEntity(id, tagEntity.getId(), idx);
          postTagRepository.save(postTagEntity);
          idx++;
        }
      }
      tagRepository.deleteOrphanTags();

      ResourceUtils.deletePostDirectory(id + "_temp");
    } catch (IOException exception) {
      exception.printStackTrace();
      ResourceUtils.deletePostDirectory(id);
      tempDirectory.renameTo(directory);
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      return ResponseDto.fileSaveError();
    } catch (Exception exception) {
      exception.printStackTrace();
      ResourceUtils.deletePostDirectory(id);
      tempDirectory.renameTo(directory);
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      return ResponseDto.databaseError();
    }

    return PutPostResponseDto.success();
  }

  @Override
  @Transactional
  public ResponseEntity<? super DeletePostResponseDto> deletePost(Integer id) {
    try {
      Optional<PostEntity> optionalPostEntity = postRepository.findById(id);
      if (optionalPostEntity.isEmpty()) return DeletePostResponseDto.notExistedPost();
      PostEntity postEntity = optionalPostEntity.get();

      postRepository.delete(postEntity);
      tagRepository.deleteOrphanTags();

      ResourceUtils.deletePostDirectory(id);
    } catch (Exception exception) {
      exception.printStackTrace();
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      return ResponseDto.databaseError();
    }

    return DeletePostResponseDto.success();
  }

}
