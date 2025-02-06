package com.hyk.hykportfolioback.service;

import com.hyk.hykportfolioback.dto.request.project.PostProjectRequestDto;
import com.hyk.hykportfolioback.dto.response.project.GetProjectListResponseDto;
import com.hyk.hykportfolioback.dto.response.project.PostProjectResponseDto;
import com.hyk.hykportfolioback.dto.response.project.PostProjectThumbnailResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ProjectService {

  ResponseEntity<? super GetProjectListResponseDto> getProjectList();

  ResponseEntity<? super PostProjectResponseDto> postProject(PostProjectRequestDto dto);

  ResponseEntity<? super PostProjectThumbnailResponseDto> postProjectThumbnail(String id, MultipartFile file);

}
