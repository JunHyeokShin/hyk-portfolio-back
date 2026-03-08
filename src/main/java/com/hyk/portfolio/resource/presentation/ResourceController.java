package com.hyk.portfolio.resource.presentation;

import java.io.IOException;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hyk.portfolio.resource.application.ResourceService;
import com.hyk.portfolio.resource.application.ResourceUploadResult;

@RequiredArgsConstructor
@RequestMapping("/resources")
@RestController
public class ResourceController {

  private final ResourceService resourceService;

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public ResourceUploadResult uploadResource(@RequestParam MultipartFile resource)
      throws IOException {
    return this.resourceService.upload(resource.getOriginalFilename(), resource.getInputStream());
  }

}
