package com.hyk.portfolio.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

  INVALID_REQUEST("요청이 올바르지 않습니다", HttpStatus.BAD_REQUEST),
  FILE_TOO_LARGE("업로드 가능한 최대 크기를 초과했습니다", HttpStatus.CONTENT_TOO_LARGE);

  private final String message;
  private final HttpStatus httpStatus;

  @Override
  public String getCode() {
    return name();
  }

}
