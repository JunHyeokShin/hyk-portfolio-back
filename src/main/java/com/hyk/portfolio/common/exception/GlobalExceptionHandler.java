package com.hyk.portfolio.common.exception;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.UriComponentsBuilder;

import com.hyk.portfolio.resource.application.exception.OrphanFileException;
import com.hyk.portfolio.resource.application.exception.StorageException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private final String errorDocBaseUri;

  public GlobalExceptionHandler(@Value("${error.doc.base-uri}") String errorDocBaseUri) {
    this.errorDocBaseUri = errorDocBaseUri;
  }

  @ExceptionHandler(BusinessException.class)
  public ProblemDetail handleBusinessException(BusinessException e) {
    ErrorCode errorCode = e.getErrorCode();
    URI typeUri = UriComponentsBuilder.fromUriString(this.errorDocBaseUri)
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

  @ExceptionHandler(StorageException.class)
  public ProblemDetail handleStorageException(StorageException e) {
    URI typeUri = UriComponentsBuilder.fromUriString(this.errorDocBaseUri)
        .pathSegment("INTERNAL_SERVER_ERROR")
        .build().toUri();

    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "An internal server error occurred during file processing."
    );
    problemDetail.setType(typeUri);
    problemDetail.setTitle("INTERNAL_SERVER_ERROR");
    return problemDetail;
  }

  @ExceptionHandler(OrphanFileException.class)
  public ProblemDetail handleOrphanFileException(OrphanFileException e) {
    URI typeUri = UriComponentsBuilder.fromUriString(this.errorDocBaseUri)
        .pathSegment("INTERNAL_SERVER_ERROR")
        .build().toUri();

    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
        HttpStatus.INTERNAL_SERVER_ERROR,
        e.getMessage()
    );
    problemDetail.setType(typeUri);
    problemDetail.setTitle("INTERNAL_SERVER_ERROR");
    return problemDetail;
  }

}
