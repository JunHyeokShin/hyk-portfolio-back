package com.hyk.portfolio.common.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

  String name();

  HttpStatus getStatus();

}
