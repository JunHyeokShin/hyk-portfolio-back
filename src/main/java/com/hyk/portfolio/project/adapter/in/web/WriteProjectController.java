package com.hyk.portfolio.project.adapter.in.web;

import java.net.URI;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hyk.portfolio.project.application.port.in.WriteProjectUseCase;
import com.hyk.portfolio.project.domain.model.Slug;

@RequiredArgsConstructor
@RestController
class WriteProjectController {

  private final WriteProjectUseCase writeProjectUseCase;

  @PostMapping("/projects")
  ResponseEntity<WriteProjectResponse> write(@Valid @RequestBody WriteProjectRequest request) {
    Slug slug = this.writeProjectUseCase.write(request.toCommand());
    return ResponseEntity.created(URI.create("/projects/" + slug.value()))
        .body(WriteProjectResponse.from(slug));
  }

}
