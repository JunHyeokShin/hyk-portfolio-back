package com.hyk.portfolio.project.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import com.hyk.portfolio.common.exception.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum ProjectErrorCode implements ErrorCode {

  PROJECT_NOT_FOUND("프로젝트를 찾을 수 없습니다", HttpStatus.NOT_FOUND),
  SLUG_DUPLICATED("이미 사용 중인 slug입니다", HttpStatus.CONFLICT);

  private final String message;
  private final HttpStatus httpStatus;

  @Override
  public String getCode() {
    return name();
  }

}
