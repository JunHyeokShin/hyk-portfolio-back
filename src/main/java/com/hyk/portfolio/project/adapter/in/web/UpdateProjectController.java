package com.hyk.portfolio.project.adapter.in.web;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hyk.portfolio.common.exception.BusinessException;
import com.hyk.portfolio.project.application.port.in.UpdateProjectUseCase;
import com.hyk.portfolio.project.domain.exception.ProjectErrorCode;
import com.hyk.portfolio.project.domain.model.Slug;

@RequiredArgsConstructor
@RestController
class UpdateProjectController {

  private final UpdateProjectUseCase updateProjectUseCase;

  private static Slug toSlug(String value) {
    try {
      return Slug.of(value);
    }
    catch (IllegalArgumentException e) {
      throw new BusinessException(ProjectErrorCode.PROJECT_NOT_FOUND, e);
    }
  }

  @PutMapping("/projects/{slug}")
  UpdateProjectResponse update(
      @PathVariable String slug,
      @Valid @RequestBody UpdateProjectRequest request
  ) {
    return UpdateProjectResponse.from(
        this.updateProjectUseCase.update(toSlug(slug), request.toCommand()));
  }

}
