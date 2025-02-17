package com.hyk.hykportfolioback.service.implement;

import com.hyk.hykportfolioback.dto.request.project.PostProjectRequestDto;
import com.hyk.hykportfolioback.dto.response.ResponseDto;
import com.hyk.hykportfolioback.dto.response.project.GetProjectContentResponseDto;
import com.hyk.hykportfolioback.dto.response.project.GetProjectListResponseDto;
import com.hyk.hykportfolioback.dto.response.project.PostProjectResponseDto;
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
    List<Path> savedPaths = new ArrayList<>();

    try {
      boolean existsId = projectRepository.existsById(id);
      if (existsId) return PostProjectResponseDto.duplicateId();

      if (thumbnailFile != null) {
        if (thumbnailFile.isEmpty()) return ResponseDto.emptyFile();

        String savePathString = createThumbnailSavePath(id, thumbnailFile);
        Path savePath = Paths.get(savePathString);
        saveFile(thumbnailFile, savePath);
        savedPaths.add(savePath);
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
          savedPaths.add(savePath);
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

  private String createThumbnailSavePath(String id, MultipartFile file) {
    String fileExtension = getFileExtension(file);
    return System.getProperty("user.dir") + "/resources/project/" + id + "/thumbnail" + fileExtension;
  }

  private String createResourceSavePath(String id, MultipartFile file) {
    return System.getProperty("user.dir") + "/resources/project/" + id + "/" + file.getOriginalFilename();
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
