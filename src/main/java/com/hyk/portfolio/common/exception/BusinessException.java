package com.hyk.portfolio.common.exception;

import java.util.Map;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

  private final ErrorCode errorCode;

  public BusinessException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public BusinessException(ErrorCode errorCode, Throwable cause) {
    super(errorCode.getMessage(), cause);
    this.errorCode = errorCode;
  }

  public BusinessException(ErrorCode errorCode, String detailMessage) {
    super(detailMessage);
    this.errorCode = errorCode;
  }

  public BusinessException(ErrorCode errorCode, String detailMessage, Throwable cause) {
    super(detailMessage, cause);
    this.errorCode = errorCode;
  }

  /**
   * RFC 9457 확장 멤버 훅 - 필요한 서브클래스에서 오버라이드
   */
  public Map<String, Object> getExtensions() {
    return Map.of();
  }

}
