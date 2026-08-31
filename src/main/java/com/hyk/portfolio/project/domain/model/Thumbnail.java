package com.hyk.portfolio.project.domain.model;

import java.util.regex.Pattern;

public record Thumbnail(String url) {

  public static final int MAX_LENGTH = 2048;
  public static final String REGEX = "^https?://\\S+$";

  private static final Pattern PATTERN = Pattern.compile(REGEX);

  public Thumbnail {
    if (url == null || url.isBlank()) {
      throw new IllegalArgumentException("url은 필수입니다");
    }
    url = url.trim();

    if (url.length() > MAX_LENGTH) {
      throw new IllegalArgumentException(
          "url은 " + MAX_LENGTH + "자를 초과할 수 없습니다: " + url);
    }
    if (!PATTERN.matcher(url).matches()) {
      throw new IllegalArgumentException("url 형식이 올바르지 않습니다: " + url);
    }
  }

  public static Thumbnail of(String url) {
    return new Thumbnail(url);
  }

}
