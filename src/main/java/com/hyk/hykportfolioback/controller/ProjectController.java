package com.hyk.hykportfolioback.controller;

import com.hyk.hykportfolioback.dto.request.project.PostProjectRequestDto;
import com.hyk.hykportfolioback.dto.response.project.GetProjectListResponseDto;
import com.hyk.hykportfolioback.dto.response.project.PostProjectResourceResponseDto;
import com.hyk.hykportfolioback.dto.response.project.PostProjectResponseDto;
import com.hyk.hykportfolioback.dto.response.project.PostProjectThumbnailResponseDto;
import com.hyk.hykportfolioback.service.ProjectService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

  @PostMapping("")
  public ResponseEntity<? super PostProjectResponseDto> postProject(@RequestBody @Valid PostProjectRequestDto requestBody) {
    ResponseEntity<? super PostProjectResponseDto> response = projectService.postProject(requestBody);
    return response;
  }

  @PostMapping("/{id}/thumbnail")
  public ResponseEntity<? super PostProjectThumbnailResponseDto> postProjectThumbnail(@PathVariable("id") @NotBlank @Size(max = 128) String id, @RequestParam MultipartFile file) {
    ResponseEntity<? super PostProjectThumbnailResponseDto> response = projectService.postProjectThumbnail(id, file);
    return response;
  }

  @PostMapping("/{id}/resource")
  public ResponseEntity<? super PostProjectResourceResponseDto> postProjectResource(@PathVariable("id") String id, @RequestParam MultipartFile file) {
    ResponseEntity<? super PostProjectResourceResponseDto> response = projectService.postProjectResource(id, file);
    return response;
  }

}
