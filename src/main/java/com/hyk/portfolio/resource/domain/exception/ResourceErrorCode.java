package com.hyk.portfolio.resource.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import com.hyk.portfolio.common.exception.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum ResourceErrorCode implements ErrorCode {

  RESOURCE_TARGET_CONFLICT("이미 다른 게시글에 연결된 리소스입니다", HttpStatus.CONFLICT);

  private final String message;
  private final HttpStatus httpStatus;

  @Override
  public String getCode() {
    return name();
  }

}
