package com.hyk.portfolio.project.domain.model;

import java.util.Locale;
import java.util.regex.Pattern;

public record ThemeColor(String code) {

  public static final int LENGTH = 7;
  public static final String REGEX = "^#[0-9a-fA-F]{6}$";

  private static final Pattern PATTERN = Pattern.compile(REGEX);

  public ThemeColor {
    if (code == null || code.isBlank()) {
      throw new IllegalArgumentException("code는 필수입니다");
    }
    if (!PATTERN.matcher(code).matches()) {
      throw new IllegalArgumentException("code 형식이 올바르지 않습니다: " + code);
    }
    code = code.toUpperCase(Locale.ROOT);
  }

  public static ThemeColor of(String code) {
    return new ThemeColor(code);
  }

}
