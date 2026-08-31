package com.hyk.portfolio.global.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.ProblemDetail;

import com.hyk.portfolio.common.exception.ErrorCode;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class ProblemDetails {

  static ProblemDetail from(ErrorCode errorCode) {
    return from(errorCode, errorCode.getMessage());
  }

  static ProblemDetail from(ErrorCode errorCode, String detail) {
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
        errorCode.getHttpStatus(), detail);
    problemDetail.setTitle(errorCode.getCode());
    return problemDetail;
  }

}
