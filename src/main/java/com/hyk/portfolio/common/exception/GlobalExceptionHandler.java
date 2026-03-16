package com.hyk.portfolio.common.exception;

import java.io.IOException;
import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.UriComponentsBuilder;

import com.hyk.portfolio.resource.application.OrphanedFileException;
import com.hyk.portfolio.resource.application.StorageException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private final String errorDocBaseUri;

  public GlobalExceptionHandler(@Value("${error.doc-base-uri}") String errorDocBaseUri) {
    this.errorDocBaseUri = errorDocBaseUri;
  }

  @ExceptionHandler({OrphanedFileException.class, StorageException.class})
  public ProblemDetail handleException(Exception e) {
    return createProblemDetail(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "INTERNAL_SERVER_ERROR",
        e.getMessage()
    );
  }

  @ExceptionHandler(IOException.class)
  public ProblemDetail handleIOException() {
    return createProblemDetail(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "INTERNAL_SERVER_ERROR",
        "An internal server error occurred."
    );
  }

  private ProblemDetail createProblemDetail(HttpStatus status, String title, String detail) {
    URI type = UriComponentsBuilder.fromUriString(this.errorDocBaseUri)
        .pathSegment(title)
        .build().toUri();
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
    problemDetail.setType(type);
    problemDetail.setTitle(title);
    return problemDetail;
  }

}
