package com.hyk.portfolio.resource.application.port.in;

import java.util.Set;

import com.hyk.portfolio.resource.domain.model.Target;

public record AttachResourcesCommand(Target target, Set<String> urls) {

  public AttachResourcesCommand {
    if (target == null) {
      throw new IllegalArgumentException("target은 필수입니다");
    }
    if (urls == null) {
      throw new IllegalArgumentException("urls는 필수입니다");
    }
    urls = Set.copyOf(urls);
  }

}
