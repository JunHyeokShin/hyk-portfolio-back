package com.hyk.portfolio.project.domain.model;

import java.util.Locale;
import java.util.regex.Pattern;

public record Slug(String value) {

  public static final int MAX_LENGTH = 128;
  public static final String REGEX = "^[a-z0-9]+(?:-[a-z0-9]+)*$";

  private static final Pattern PATTERN = Pattern.compile(REGEX);

  public Slug {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException("value는 필수입니다");
    }
    value = value.trim().toLowerCase(Locale.ROOT);

    if (value.length() > MAX_LENGTH) {
      throw new IllegalArgumentException(
          "value는 " + MAX_LENGTH + "자를 초과할 수 없습니다: " + value);
    }
    if (!PATTERN.matcher(value).matches()) {
      throw new IllegalArgumentException("value 형식이 올바르지 않습니다: " + value);
    }
  }

  public static Slug of(String value) {
    return new Slug(value);
  }

}
