package com.hyk.portfolio.common.exception;

import java.net.URI;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hyk.portfolio.common.config.AppProperties;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private final AppProperties appProperties;

  @ExceptionHandler(BusinessException.class)
  public ProblemDetail handleBusinessException(BusinessException e) {
    ErrorCode errorCode = e.getErrorCode();
    String typeUrl = this.appProperties.error().docBaseUrl() + "/" + errorCode.name();

    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
        errorCode.getStatus(),
        e.getMessage()
    );
    problemDetail.setType(URI.create(typeUrl));
    problemDetail.setTitle(errorCode.name());

    return problemDetail;
  }

}
