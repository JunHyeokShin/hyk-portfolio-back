package com.hyk.portfolio.resource.application;

import lombok.Getter;

@Getter
public class OrphanedFileException extends RuntimeException {

  private final String orphanedFilename;

  public OrphanedFileException(String orphanedFilename) {
    super("Failed to delete file after database transaction rollback. Orphaned file created: " + orphanedFilename);
    this.orphanedFilename = orphanedFilename;
  }

}
