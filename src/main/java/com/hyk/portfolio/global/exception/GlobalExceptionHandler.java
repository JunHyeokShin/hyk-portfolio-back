package com.hyk.portfolio.global.exception;

import jakarta.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import com.hyk.portfolio.common.exception.BusinessException;
import com.hyk.portfolio.common.exception.CommonErrorCode;
import com.hyk.portfolio.common.exception.ErrorCode;

@Slf4j
@RestControllerAdvice
class GlobalExceptionHandler {

  private static String describe(Exception e, HttpServletRequest request) {
    return "%s %s from %s (%s)".formatted(
        request.getMethod(),
        request.getRequestURI(),
        request.getRemoteAddr(),
        e.getClass().getSimpleName());
  }

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

  @ExceptionHandler(AuthenticationException.class)
  ProblemDetail handleAuthenticationException(
      AuthenticationException e, HttpServletRequest request) {
    if (e instanceof BadCredentialsException) {
      log.warn("잘못된 인증 정보: {}", describe(e, request));
    }
    else {
      log.info("인증 없는 접근: {}", describe(e, request));
    }
    return ProblemDetails.from(CommonErrorCode.UNAUTHORIZED);
  }

  @ExceptionHandler(AccessDeniedException.class)
  ProblemDetail handleAccessDeniedException(
      AccessDeniedException e, HttpServletRequest request) {
    log.warn("권한 없는 접근: {}", describe(e, request));
    return ProblemDetails.from(CommonErrorCode.FORBIDDEN);
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
