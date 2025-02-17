package com.hyk.hykportfolioback.service;

import com.hyk.hykportfolioback.dto.request.project.PostProjectRequestDto;
import com.hyk.hykportfolioback.dto.response.project.GetProjectContentResponseDto;
import com.hyk.hykportfolioback.dto.response.project.GetProjectListResponseDto;
import com.hyk.hykportfolioback.dto.response.project.PostProjectResponseDto;
import org.springframework.http.ResponseEntity;

public interface ProjectService {

  ResponseEntity<? super GetProjectListResponseDto> getProjectList();

  ResponseEntity<? super GetProjectContentResponseDto> getProjectContent(String id);

  ResponseEntity<? super PostProjectResponseDto> postProject(PostProjectRequestDto dto);

}
