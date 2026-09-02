package com.hyk.portfolio.project.application.port.out;

import com.hyk.portfolio.project.domain.event.ProjectWritten;

public interface PublishProjectEventPort {

  void publish(ProjectWritten event);

}
