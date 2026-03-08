package com.hyk.portfolio.resource.exception;

import lombok.Getter;

@Getter
public class OrphanFileException extends RuntimeException {

  private final String orphanFilename;

  public OrphanFileException(String orphanFilename) {
    super("Failed to delete file after database transaction rollback. Orphan file created: " + orphanFilename);
    this.orphanFilename = orphanFilename;
  }

}
