package com.hyk.portfolio.resource.presentation;

import java.io.IOException;
import java.net.URI;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hyk.portfolio.resource.application.ResourceService;
import com.hyk.portfolio.resource.application.dto.ResourceDetail;
import com.hyk.portfolio.resource.presentation.dto.ResourceDetailResponse;

@RequiredArgsConstructor
@RequestMapping("/resources")
@RestController
public class ResourceController {

  private final ResourceService resourceService;

  @PostMapping
  public ResponseEntity<ResourceDetailResponse> uploadResource(@RequestParam MultipartFile resource)
      throws IOException {
    ResourceDetail result = this.resourceService.uploadResource(
        resource.getOriginalFilename(), resource.getInputStream());
    ResourceDetailResponse response = ResourceDetailResponse.from(result);
    URI location = URI.create(response.url());
    return ResponseEntity.created(location).body(response);
  }

}
