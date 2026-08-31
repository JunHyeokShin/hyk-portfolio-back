package com.hyk.portfolio.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import com.hyk.portfolio.common.exception.BusinessException;
import com.hyk.portfolio.common.exception.CommonErrorCode;
import com.hyk.portfolio.common.exception.ErrorCode;

@Slf4j
@RestControllerAdvice
class GlobalExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  ProblemDetail handleBusinessException(BusinessException e) {
    ErrorCode errorCode = e.getErrorCode();
    if (errorCode.getHttpStatus().is5xxServerError()) {
      log.error("서버 오류 응답: {}", errorCode.getCode(), e);
    }
    ProblemDetail problemDetail = ProblemDetails.from(errorCode, e.getMessage());
    e.getExtensions().forEach(problemDetail::setProperty);
    return problemDetail;
  }

  @ExceptionHandler(MultipartException.class)
  ProblemDetail handleMultipartException(MultipartException e) {
    return ProblemDetails.from(CommonErrorCode.INVALID_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  ProblemDetail handleException(Exception e) {
    log.error("예외 발생", e);
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
        HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다");
    problemDetail.setTitle("INTERNAL_SERVER_ERROR");
    return problemDetail;
  }

}
