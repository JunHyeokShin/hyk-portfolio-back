package com.hyk.portfolio.project.application.port.in;

import com.hyk.portfolio.project.domain.model.Project;
import com.hyk.portfolio.project.domain.model.Slug;

public interface ReadProjectUseCase {

  Project read(Slug slug);

}
