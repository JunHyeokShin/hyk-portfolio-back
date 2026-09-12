package com.hyk.portfolio.resource.application.port.in;

import java.io.InputStream;

import com.hyk.portfolio.resource.domain.model.Extension;

public record UploadResourceCommand(Extension extension, InputStream content) {

  public UploadResourceCommand {
    if (extension == null) {
      throw new IllegalArgumentException("extension은 필수입니다");
    }
    if (content == null) {
      throw new IllegalArgumentException("content는 필수입니다");
    }
  }

}
