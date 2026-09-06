package com.hyk.portfolio.project.application.port.out;

import com.hyk.portfolio.project.domain.event.ProjectDeleted;
import com.hyk.portfolio.project.domain.event.ProjectUpdated;
import com.hyk.portfolio.project.domain.event.ProjectWritten;

public interface PublishProjectEventPort {

  void publish(ProjectWritten event);

  void publish(ProjectUpdated event);

  void publish(ProjectDeleted event);

}
