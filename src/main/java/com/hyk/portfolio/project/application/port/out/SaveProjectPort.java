package com.hyk.portfolio.project.application.port.out;

import com.hyk.portfolio.project.domain.model.Project;

public interface SaveProjectPort {

  Project save(Project project);

}
