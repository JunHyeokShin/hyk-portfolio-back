package com.hyk.portfolio.resource.application.port.in;

import java.io.InputStream;

public record UploadResourceCommand(String originalFilename, InputStream content) {

  // TODO: 유효성 검사 (IAE)

}
