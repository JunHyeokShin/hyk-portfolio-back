package com.hyk.portfolio.project.domain.event;

public record ProjectDeleted(Long projectId) {

  public ProjectDeleted {
    if (projectId == null) {
      throw new IllegalArgumentException("projectId는 필수입니다");
    }
  }

}
