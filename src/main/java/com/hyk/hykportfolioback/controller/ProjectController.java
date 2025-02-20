package com.hyk.hykportfolioback.controller;

import com.hyk.hykportfolioback.dto.request.project.PostProjectRequestDto;
import com.hyk.hykportfolioback.dto.request.project.PutProjectRequestDto;
import com.hyk.hykportfolioback.dto.response.project.*;
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

  @GetMapping("/{id}")
  public ResponseEntity<? super GetProjectResponseDto> getProject(@PathVariable("id") String id) {
    ResponseEntity<? super GetProjectResponseDto> response = projectService.getProject(id);
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

  @PutMapping("/{id}")
  public ResponseEntity<? super PutProjectResponseDto> putProject(@PathVariable("id") String id,
                                                                  @ModelAttribute @Valid PutProjectRequestDto request) {
    ResponseEntity<? super PutProjectResponseDto> response = projectService.updateProject(id, request);
    return response;
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<? super DeleteProjectResponseDto> deleteProject(@PathVariable("id") String id) {
    ResponseEntity<? super DeleteProjectResponseDto> response = projectService.deleteProject(id);
    return response;
  }

}
