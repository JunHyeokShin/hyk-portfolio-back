package com.hyk.hykportfolioback.service;

import com.hyk.hykportfolioback.dto.request.project.PostProjectRequestDto;
import com.hyk.hykportfolioback.dto.response.project.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProjectService {

  ResponseEntity<? super GetProjectListResponseDto> getProjectList();

  ResponseEntity<? super GetProjectContentResponseDto> getProjectContent(String id);

  ResponseEntity<? super PostProjectResponseDto> postProject(PostProjectRequestDto dto);

  ResponseEntity<? super PostProjectThumbnailResponseDto> postProjectThumbnail(String id, MultipartFile file);

  ResponseEntity<? super PostProjectResourcesResponseDto> postProjectResources(String id, List<MultipartFile> files);

}
