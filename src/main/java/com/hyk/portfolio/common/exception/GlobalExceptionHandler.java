package com.hyk.portfolio.common.exception;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.UriComponentsBuilder;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private final String errorDocBaseUri;

  public GlobalExceptionHandler(@Value("${error.doc.base-uri}") String errorDocBaseUri) {
    this.errorDocBaseUri = errorDocBaseUri;
  }

  @ExceptionHandler(BusinessException.class)
  public ProblemDetail handleBusinessException(BusinessException e) {
    ErrorCode errorCode = e.getErrorCode();
    URI typeUri = UriComponentsBuilder
        .fromUriString(this.errorDocBaseUri)
        .pathSegment(errorCode.name())
        .build().toUri();

    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
        errorCode.getStatus(),
        e.getMessage()
    );
    problemDetail.setType(typeUri);
    problemDetail.setTitle(errorCode.name());

    return problemDetail;
  }

}
