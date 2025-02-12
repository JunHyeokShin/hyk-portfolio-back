package com.hyk.hykportfolioback.service.implement;

import com.hyk.hykportfolioback.dto.request.project.PostProjectRequestDto;
import com.hyk.hykportfolioback.dto.response.ResponseDto;
import com.hyk.hykportfolioback.dto.response.project.*;
import com.hyk.hykportfolioback.entity.ProjectEntity;
import com.hyk.hykportfolioback.repository.ProjectRepository;
import com.hyk.hykportfolioback.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
public class ProjectServiceImplement implements ProjectService {

  @Value("${domain}")
  private String domain;

  private final ProjectRepository projectRepository;

  @Override
  public ResponseEntity<? super GetProjectListResponseDto> getProjectList() {
    List<ProjectEntity> projectEntities = new ArrayList<>();

    try {
      projectEntities = projectRepository.findAll();
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
    try {
      String id = dto.getId();
      boolean existsId = projectRepository.existsById(id);
      if (existsId) return PostProjectResponseDto.duplicateId();

      ProjectEntity projectEntity = new ProjectEntity(dto);
      projectRepository.save(projectEntity);
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.databaseError();
    }

    return PostProjectResponseDto.success();
  }

  @Override
  public ResponseEntity<? super PostProjectThumbnailResponseDto> postProjectThumbnail(String id, MultipartFile file) {
    if (file.isEmpty()) return ResponseDto.emptyFile();

    try {
      String savePathString = createThumbnailSavePath(id, file);
      Path savePath = Paths.get(savePathString);
      deleteExistingThumbnail(savePath);
      saveFile(file, savePath);
      String url = domain + "/resources/project/" + id + "/thumbnail" + getFileExtension(file);
      return PostProjectThumbnailResponseDto.success(url);
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.fileSaveError();
    }
  }

  @Override
  public ResponseEntity<? super PostProjectResourcesResponseDto> postProjectResources(String id,
                                                                                      List<MultipartFile> files) {
    List<String> urls = new ArrayList<>();
    List<Path> savedFiles = new ArrayList<>();

    for (MultipartFile file : files) {
      if (file.isEmpty()) {
        rollback(savedFiles);
        return ResponseDto.emptyFile();
      }

      try {
        String savePathString = createResourceSavePath(id, file);
        Path savePath = Paths.get(savePathString);
        saveFile(file, savePath);
        savedFiles.add(savePath);
        String url = domain + "/resources/project/" + id + "/" + file.getOriginalFilename();
        urls.add(url);
      } catch (Exception exception) {
        exception.printStackTrace();
        rollback(savedFiles);
        return ResponseDto.fileSaveError();
      }
    }

    return PostProjectResourcesResponseDto.success(urls);
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

  private void deleteExistingThumbnail(Path savePath) throws IOException {
    File parentDirectory = savePath.getParent().toFile();
    File[] resources = parentDirectory.listFiles();

    if (resources == null) return;

    for (File resource : resources) {
      if (isThumbnailFile(resource)) resource.delete();
    }

  }

  private boolean isThumbnailFile(File file) {
    if (!file.isFile()) return false;

    String resourceFilenameWithoutExtension = file.getName().substring(0, file.getName().lastIndexOf("."));
    return resourceFilenameWithoutExtension.equals("thumbnail");
  }

  private void saveFile(MultipartFile file, Path savePath) throws IOException {
    Files.createDirectories(savePath.getParent());
    file.transferTo(savePath.toFile());
  }

  private void rollback(List<Path> savedFiles) {
    for (Path filePath : savedFiles) {
      try {
        Files.deleteIfExists(filePath);
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    }
  }

}
