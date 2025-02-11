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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImplement implements ProjectService {

  @Value("${server.domain}")
  private String domain;
  @Value("${server.port}")
  private String port;

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
      File directory = new File(System.getProperty("user.dir") + "/resources/project/" + id + "/");
      File[] resources = directory.listFiles();
      if (resources != null) {
        for (File resource : resources) {
          if (resource.isFile() && resource.getName().substring(0, resource.getName().lastIndexOf("."))
                                           .equals("thumbnail")) {
            resource.delete();
          }
        }
      }

      String originalFilename = file.getOriginalFilename();
      String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
      String savePath = System.getProperty("user.dir") + "/resources/project/" + id + "/thumbnail" + extension;

      Path path = Paths.get(savePath);
      Files.createDirectories(path.getParent());
      file.transferTo(path.toFile());

      return PostProjectThumbnailResponseDto.success("http://" + domain + ":" + port + "/resources/project/" + id +
          "/thumbnail" + extension);
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.fileSaveError();
    }
  }

  @Override
  public ResponseEntity<? super PostProjectResourceResponseDto> postProjectResource(String id, MultipartFile file) {
    if (file.isEmpty()) return ResponseDto.emptyFile();

    try {
      String savePath = System.getProperty("user.dir") + "/resources/project/" + id + "/" + file.getOriginalFilename();

      Path path = Paths.get(savePath);
      Files.createDirectories(path.getParent());
      file.transferTo(path.toFile());

      return PostProjectResourceResponseDto.success("http://" + domain + ":" + port + "/resources/project/" + id +
          "/" + file.getOriginalFilename());
    } catch (Exception exception) {
      exception.printStackTrace();
      return ResponseDto.fileSaveError();
    }
  }

}
