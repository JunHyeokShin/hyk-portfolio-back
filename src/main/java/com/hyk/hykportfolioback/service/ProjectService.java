package com.hyk.hykportfolioback.service;

import com.hyk.hykportfolioback.dto.request.project.PostProjectRequestDto;
import com.hyk.hykportfolioback.dto.request.project.PutProjectRequestDto;
import com.hyk.hykportfolioback.dto.response.project.*;
import org.springframework.http.ResponseEntity;

public interface ProjectService {

  ResponseEntity<? super GetProjectListResponseDto> getProjectList();

  ResponseEntity<? super GetProjectResponseDto> getProject(String id);

  ResponseEntity<? super GetProjectContentResponseDto> getProjectContent(String id);

  ResponseEntity<? super PostProjectResponseDto> postProject(PostProjectRequestDto dto);

  ResponseEntity<? super PutProjectResponseDto> updateProject(String id, PutProjectRequestDto dto);

}
