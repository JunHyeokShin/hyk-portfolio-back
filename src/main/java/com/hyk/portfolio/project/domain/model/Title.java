package com.hyk.portfolio.project.domain.model;

public record Title(String value) {

  public static final int MAX_LENGTH = 128;

  public Title {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException("value는 필수입니다");
    }
    value = value.trim();

    if (value.length() > MAX_LENGTH) {
      throw new IllegalArgumentException(
          "value는 " + MAX_LENGTH + "자를 초과할 수 없습니다: " + value);
    }
  }

  public static Title of(String value) {
    return new Title(value);
  }

}
