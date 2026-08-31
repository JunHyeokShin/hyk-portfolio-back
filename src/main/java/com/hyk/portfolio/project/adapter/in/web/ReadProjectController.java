package com.hyk.portfolio.project.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hyk.portfolio.common.exception.BusinessException;
import com.hyk.portfolio.project.application.port.in.ReadProjectUseCase;
import com.hyk.portfolio.project.domain.exception.ProjectErrorCode;
import com.hyk.portfolio.project.domain.model.Project;
import com.hyk.portfolio.project.domain.model.Slug;

@RequiredArgsConstructor
@RestController
class ReadProjectController {

  private final ReadProjectUseCase readProjectUseCase;

  private static Slug toSlug(String value) {
    try {
      return Slug.of(value);
    }
    catch (IllegalArgumentException e) {
      throw new BusinessException(ProjectErrorCode.PROJECT_NOT_FOUND, e);
    }
  }

  @GetMapping("/projects/{slug}")
  ResponseEntity<ReadProjectResponse> read(@PathVariable String slug) {
    Project project = this.readProjectUseCase.read(toSlug(slug));
    return ResponseEntity.ok(ReadProjectResponse.from(project));
  }

}
