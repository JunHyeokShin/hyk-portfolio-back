package com.hyk.portfolio.project.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyk.portfolio.project.application.port.in.ListProjectsUseCase;

@RequiredArgsConstructor
@RestController
class ListProjectsController {

  private final ListProjectsUseCase listProjectsUseCase;

  @GetMapping("/projects")
  Page<ProjectSummaryResponse> list(
      @RequestParam(required = false) String keyword,
      @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
  ) {
    keyword = keyword != null && !keyword.isBlank() ? keyword.trim() : null;
    return this.listProjectsUseCase.list(keyword, pageable)
        .map(ProjectSummaryResponse::from);
  }

}
