package com.hyk.portfolio.resource.application.exception;

import lombok.Getter;

@Getter
public class OrphanFileException extends RuntimeException {

  private final String orphanFilename;

  public OrphanFileException(String message, String orphanFilename, Throwable cause) {
    super(message, cause);
    this.orphanFilename = orphanFilename;
  }

}
