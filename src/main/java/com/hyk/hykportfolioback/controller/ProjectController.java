package com.hyk.hykportfolioback.controller;

import com.hyk.hykportfolioback.dto.request.project.PostProjectRequestDto;
import com.hyk.hykportfolioback.dto.response.project.GetProjectContentResponseDto;
import com.hyk.hykportfolioback.dto.response.project.GetProjectListResponseDto;
import com.hyk.hykportfolioback.dto.response.project.PostProjectResponseDto;
import com.hyk.hykportfolioback.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
public class ProjectController {

  private final ProjectService projectService;

  @GetMapping("")
  public ResponseEntity<? super GetProjectListResponseDto> getProjectList() {
    ResponseEntity<? super GetProjectListResponseDto> response = projectService.getProjectList();
    return response;
  }

  @GetMapping("/{id}/content")
  public ResponseEntity<? super GetProjectContentResponseDto> getProjectContent(@PathVariable("id") String id) {
    ResponseEntity<? super GetProjectContentResponseDto> response = projectService.getProjectContent(id);
    return response;
  }

  @PostMapping("")
  public ResponseEntity<? super PostProjectResponseDto> postProject(@ModelAttribute @Valid PostProjectRequestDto request) {
    ResponseEntity<? super PostProjectResponseDto> response = projectService.postProject(request);
    return response;
  }

}
