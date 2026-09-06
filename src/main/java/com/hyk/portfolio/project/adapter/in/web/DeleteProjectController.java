package com.hyk.portfolio.project.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hyk.portfolio.common.exception.BusinessException;
import com.hyk.portfolio.project.application.port.in.DeleteProjectUseCase;
import com.hyk.portfolio.project.domain.exception.ProjectErrorCode;
import com.hyk.portfolio.project.domain.model.Slug;

@RequiredArgsConstructor
@RestController
class DeleteProjectController {

  private final DeleteProjectUseCase deleteProjectUseCase;

  private static Slug toSlug(String value) {
    try {
      return Slug.of(value);
    }
    catch (IllegalArgumentException e) {
      throw new BusinessException(ProjectErrorCode.PROJECT_NOT_FOUND, e);
    }
  }

  @DeleteMapping("/projects/{slug}")
  ResponseEntity<Void> delete(@PathVariable String slug) {
    this.deleteProjectUseCase.delete(toSlug(slug));
    return ResponseEntity.noContent().build();
  }

}
