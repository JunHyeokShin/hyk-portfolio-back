package com.hyk.portfolio.project.application.port.out;

import com.hyk.portfolio.project.domain.model.Project;

public interface SaveProjectPort {

  /**
   * slug가 이미 사용 중이면 SLUG_DUPLICATED로 실패한다.
   */
  Project save(Project project);

}
