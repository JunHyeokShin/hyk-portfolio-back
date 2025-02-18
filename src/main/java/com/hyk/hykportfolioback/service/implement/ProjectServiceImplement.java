package com.hyk.hykportfolioback.service.implement;

import com.hyk.hykportfolioback.dto.object.ResourceListItem;
import com.hyk.hykportfolioback.dto.request.project.PostProjectRequestDto;
import com.hyk.hykportfolioback.dto.request.project.PutProjectRequestDto;
import com.hyk.hykportfolioback.dto.response.ResponseDto;
import com.hyk.hykportfolioback.dto.response.project.*;
import com.hyk.hykportfolioback.entity.ProjectEntity;
import com.hyk.hykportfolioback.repository.ProjectRepository;
import com.hyk.hykportfolioback.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImplement implements ProjectService {

  @Value("${domain}")
  private String domain;

  private final ProjectRepository projectRepository;

  @Override
  public ResponseEntity<? super GetProjectListResponseDto> getProjectList() {
    List<ProjectEntity> projectEntities = new ArrayList<>();

    try {
      projectEntities = projectRepository.findAllByOrderByCreatedAtDesc();
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetProjectListResponseDto.success(projectEntities);
  }

  @Override
  public ResponseEntity<? super GetProjectResponseDto> getProject(String id) {
    ProjectEntity projectEntity = null;
    List<ResourceListItem> resourceList = new ArrayList<>();

    try {
      Optional<ProjectEntity> optionalProjectEntity = projectRepository.findById(id);
      if (optionalProjectEntity.isEmpty()) return GetProjectResponseDto.notExistedProject();
      projectEntity = optionalProjectEntity.get();

      File directory = new File(System.getProperty("user.dir") + "/resources/project/" + id);
      File[] resources = directory.listFiles();

      if (resources == null) return GetProjectResponseDto.success(projectEntity, resourceList);

      for (File resource : resources) {
        if (!isThumbnailFile(resource)) {
          String fileName = resource.getName();
          String url = domain + "/resources/project/" + id + "/" + fileName;
          ResourceListItem resourceListItem = new ResourceListItem(fileName, url);
          resourceList.add(resourceListItem);
        }
      }
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetProjectResponseDto.success(projectEntity, resourceList);
  }

  @Override
  public ResponseEntity<? super GetProjectContentResponseDto> getProjectContent(String id) {
    ProjectEntity projectEntity = null;

    try {
      Optional<ProjectEntity> optionalProjectEntity = projectRepository.findById(id);
      if (optionalProjectEntity.isEmpty()) return GetProjectContentResponseDto.notExistedProject();
      projectEntity = optionalProjectEntity.get();
      projectEntity.increaseViewCount();
      projectRepository.save(projectEntity);
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return GetProjectContentResponseDto.success(projectEntity);
  }

  @Override
  public ResponseEntity<? super PostProjectResponseDto> postProject(PostProjectRequestDto dto) {
    String id = dto.getId();
    String thumbnail = null;
    MultipartFile thumbnailFile = dto.getThumbnailFile();
    List<MultipartFile> resourceFiles = dto.getResourceFiles();

    try {
      boolean existsId = projectRepository.existsById(id);
      if (existsId) return PostProjectResponseDto.duplicateId();

      if (thumbnailFile != null) {
        if (thumbnailFile.isEmpty()) return ResponseDto.emptyFile();

        String savePathString = createThumbnailSavePath(id, thumbnailFile);
        Path savePath = Paths.get(savePathString);
        saveFile(thumbnailFile, savePath);
        thumbnail = domain + "/resources/project/" + id + "/thumbnail" + getFileExtension(thumbnailFile);
      }

      if (resourceFiles != null) {
        for (MultipartFile resourceFile : resourceFiles) {
          if (resourceFile.isEmpty()) {
            deleteProjectDirectory(id);
            return ResponseDto.emptyFile();
          }
          String savePathString = createResourceSavePath(id, resourceFile);
          Path savePath = Paths.get(savePathString);
          saveFile(resourceFile, savePath);
        }
      }

      ProjectEntity projectEntity = new ProjectEntity(dto, thumbnail);
      projectRepository.save(projectEntity);
    } catch (IOException exception) {
      exception.printStackTrace();
      deleteProjectDirectory(id);
      return ResponseDto.fileSaveError();
    } catch (Exception exception) {
      exception.printStackTrace();
      deleteProjectDirectory(id);
      return ResponseDto.databaseError();
    }

    return PostProjectResponseDto.success();
  }

  @Override
  public ResponseEntity<? super PutProjectResponseDto> updateProject(String id, PutProjectRequestDto dto) {
    String thumbnail = null;
    MultipartFile thumbnailFile = dto.getThumbnailFile();
    List<MultipartFile> resourceFiles = dto.getResourceFiles();
    File directory = new File(System.getProperty("user.dir") + "/resources/project/" + id);
    File tempDirectory = new File(System.getProperty("user.dir") + "/resources/project/" + id + "_temp");
    Optional<ProjectEntity> optionalProjectEntity;

    try {
      directory.renameTo(tempDirectory);

      optionalProjectEntity = projectRepository.findById(id);
      if (optionalProjectEntity.isEmpty()) {
        tempDirectory.renameTo(directory);
        return PutProjectResponseDto.notExistedProject();
      }

      if (thumbnailFile != null) {
        if (thumbnailFile.isEmpty()) {
          tempDirectory.renameTo(directory);
          return ResponseDto.emptyFile();
        }
        String savePathString = createThumbnailSavePath(id, thumbnailFile);
        Path savePath = Paths.get(savePathString);
        saveFile(thumbnailFile, savePath);
        thumbnail = domain + "/resources/project/" + id + "/thumbnail" + getFileExtension(thumbnailFile);
      }

      if (resourceFiles != null) {
        for (MultipartFile resourceFile : resourceFiles) {
          if (resourceFile.isEmpty()) {
            deleteProjectDirectory(id);
            return ResponseDto.emptyFile();
          }
          String savePathString = createResourceSavePath(id, resourceFile);
          Path savePath = Paths.get(savePathString);
          saveFile(resourceFile, savePath);
        }
      }

      ProjectEntity projectEntity = optionalProjectEntity.get();
      projectEntity.updateProject(dto, thumbnail);
      projectRepository.save(projectEntity);

      deleteProjectDirectory(id + "_temp");
    } catch (IOException exception) {
      exception.printStackTrace();
      deleteProjectDirectory(id);
      tempDirectory.renameTo(directory);
      return ResponseDto.fileSaveError();
    } catch (Exception exception) {
      exception.printStackTrace();
      deleteProjectDirectory(id);
      tempDirectory.renameTo(directory);
      return ResponseDto.databaseError();
    }

    return PutProjectResponseDto.success();
  }

  private String createThumbnailSavePath(String id, MultipartFile file) {
    String fileExtension = getFileExtension(file);
    return System.getProperty("user.dir") + "/resources/project/" + id + "/thumbnail" + fileExtension;
  }

  private String createResourceSavePath(String id, MultipartFile file) {
    return System.getProperty("user.dir") + "/resources/project/" + id + "/" + file.getOriginalFilename();
  }

  private boolean isThumbnailFile(File file) {
    if (!file.isFile()) return false;

    String resourceFilenameWithoutExtension = file.getName().substring(0, file.getName().lastIndexOf("."));
    return resourceFilenameWithoutExtension.equals("thumbnail");
  }

  private String getFileExtension(MultipartFile file) {
    String originalFilename = file.getOriginalFilename();
    return originalFilename.substring(originalFilename.lastIndexOf("."));
  }

  private void saveFile(MultipartFile file, Path savePath) throws IOException {
    Files.createDirectories(savePath.getParent());
    file.transferTo(savePath.toFile());
  }

  private void deleteProjectDirectory(String id) {
    try {
      Path path = Paths.get(System.getProperty("user.dir") + "/resources/project/" + id);
      FileUtils.deleteDirectory(path.toFile());
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

}
