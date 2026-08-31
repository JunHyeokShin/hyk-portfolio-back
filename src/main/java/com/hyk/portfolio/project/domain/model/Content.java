package com.hyk.portfolio.project.domain.model;

public record Content(String value) {

  public Content {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException("value는 필수입니다");
    }
  }

  public static Content of(String value) {
    return new Content(value);
  }

}
