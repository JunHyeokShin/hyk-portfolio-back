package com.hyk.portfolio.resource.adapter.in.web;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hyk.portfolio.common.exception.BusinessException;
import com.hyk.portfolio.resource.application.port.in.UploadResourceCommand;
import com.hyk.portfolio.resource.application.port.in.UploadResourceResult;
import com.hyk.portfolio.resource.application.port.in.UploadResourceUseCase;
import com.hyk.portfolio.resource.domain.exception.ResourceErrorCode;
import com.hyk.portfolio.resource.domain.model.Extension;

@RequiredArgsConstructor
@RestController
class UploadResourceController {

  private final UploadResourceUseCase uploadResourceUseCase;

  private static Extension toExtension(String originalFilename) {
    try {
      return Extension.of(extractExtension(originalFilename));
    }
    catch (IllegalArgumentException e) {
      throw new BusinessException(ResourceErrorCode.UNSUPPORTED_RESOURCE_TYPE, e);
    }
  }

  private static String extractExtension(String originalFilename) {
    if (originalFilename == null) {
      return "";
    }
    int dotIndex = originalFilename.lastIndexOf('.');
    return dotIndex < 0 ? "" : originalFilename.substring(dotIndex + 1);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/resources")
  ResponseEntity<UploadResourceResponse> upload(@RequestPart MultipartFile file)
      throws IOException {
    if (file.isEmpty()) {
      throw new BusinessException(ResourceErrorCode.EMPTY_RESOURCE);
    }
    Extension extension = toExtension(file.getOriginalFilename());
    try (InputStream content = file.getInputStream()) {
      UploadResourceResult result = this.uploadResourceUseCase.upload(
          new UploadResourceCommand(extension, content));
      return ResponseEntity.created(URI.create(result.url()))
          .body(UploadResourceResponse.from(result));
    }
  }

}
