package com.hyk.portfolio.common.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

  HttpStatus getStatus();

  String name();

}
