package com.hyk.portfolio.project.application.port.in;

import com.hyk.portfolio.project.domain.model.Slug;

public interface WriteProjectUseCase {

  Slug write(WriteProjectCommand command);

}
