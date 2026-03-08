package com.hyk.portfolio.common.exception;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.UriComponentsBuilder;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private final String errorDocBaseUri;

  public GlobalExceptionHandler(@Value("${error.doc-base-uri}") String errorDocBaseUri) {
    this.errorDocBaseUri = errorDocBaseUri;
  }

  @ExceptionHandler(BusinessException.class)
  public ProblemDetail handleBusinessException(BusinessException e) {
    ErrorCode errorCode = e.getErrorCode();
    return createProblemDetail(errorCode.getStatus(), errorCode.name(), e.getMessage());
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
