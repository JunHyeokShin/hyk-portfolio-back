package com.hyk.portfolio.project.domain.event;

import java.util.Set;

public record ProjectWritten(Long projectId, Set<String> referencedUrls) {

  public ProjectWritten {
    if (projectId == null) {
      throw new IllegalArgumentException("projectId는 필수입니다");
    }
    if (referencedUrls == null) {
      throw new IllegalArgumentException("referencedUrls는 필수입니다");
    }
    referencedUrls = Set.copyOf(referencedUrls);
  }

}
