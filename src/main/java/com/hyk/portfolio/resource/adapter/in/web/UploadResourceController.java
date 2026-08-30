package com.hyk.portfolio.resource.adapter.in.web;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hyk.portfolio.resource.application.port.in.UploadResourceCommand;
import com.hyk.portfolio.resource.application.port.in.UploadResourceResult;
import com.hyk.portfolio.resource.application.port.in.UploadResourceUseCase;

@RequiredArgsConstructor
@RestController
class UploadResourceController {

  private final UploadResourceUseCase uploadResourceUseCase;

  @PostMapping("/resources")
  ResponseEntity<UploadResourceResponse> upload(@RequestPart MultipartFile file)
      throws IOException {
    // TODO: 유효성 검사
    try (InputStream content = file.getInputStream()) {
      UploadResourceResult result = this.uploadResourceUseCase.upload(
          new UploadResourceCommand(file.getOriginalFilename(), content));
      return ResponseEntity.created(URI.create(result.url()))
          .body(UploadResourceResponse.from(result));
    }
  }

}
