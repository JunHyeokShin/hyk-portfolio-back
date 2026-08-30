package com.hyk.portfolio.resource.application.port.in;

import com.hyk.portfolio.resource.domain.model.Resource;

public record UploadResourceResult(Long id, String url) {

  public static UploadResourceResult from(Resource resource) {
    return new UploadResourceResult(resource.getId(), resource.getUrl());
  }

}
