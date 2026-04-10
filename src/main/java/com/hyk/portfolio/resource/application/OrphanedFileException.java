package com.hyk.portfolio.resource.application;

import lombok.Getter;

@Getter
public class OrphanedFileException extends RuntimeException {

  private final String orphanedFilename;

  public OrphanedFileException(String orphanedFilename) {
    super("Orphaned file created: " + orphanedFilename);
    this.orphanedFilename = orphanedFilename;
  }

}
