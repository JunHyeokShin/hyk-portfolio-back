package com.hyk.portfolio.project.adapter.out.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.hyk.portfolio.project.application.port.out.PublishProjectEventPort;
import com.hyk.portfolio.project.domain.event.ProjectWritten;

@RequiredArgsConstructor
@Component
class SpringProjectEventPublisher implements PublishProjectEventPort {

  private final ApplicationEventPublisher delegate;

  @Override
  public void publish(ProjectWritten event) {
    this.delegate.publishEvent(event);
  }

}
