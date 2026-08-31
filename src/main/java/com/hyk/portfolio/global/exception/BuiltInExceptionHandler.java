package com.hyk.portfolio.global.exception;

import java.util.Map;
import java.util.Objects;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hyk.portfolio.common.exception.CommonErrorCode;
import com.hyk.portfolio.common.exception.ErrorCode;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
class BuiltInExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  ProblemDetail handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
    ErrorCode errorCode = CommonErrorCode.INVALID_REQUEST;
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
        errorCode.getHttpStatus(), errorCode.getMessage());
    problemDetail.setTitle(errorCode.getCode());
    problemDetail.setProperty("errors", e.getFieldErrors().stream()
        .map(fieldError -> Map.of(
            "field", fieldError.getField(),
            "message", Objects.requireNonNullElse(
                fieldError.getDefaultMessage(), "올바르지 않은 값입니다")))
        .toList());
    return problemDetail;
  }

}
