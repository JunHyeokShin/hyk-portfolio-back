package com.hyk.portfolio.project.application.port.in;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListProjectsUseCase {

  Page<ProjectSummary> list(String keyword, Pageable pageable);

}
