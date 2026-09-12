package com.hyk.portfolio.resource.domain.model;

import java.util.Locale;
import java.util.Set;

public record Extension(String value) {

  private static final Set<String> ALLOWED = Set.of(
      "png", "jpg", "jpeg", "gif", "webp",
      "mp4", "webm",
      "mp3", "m4a",
      "pdf");

  public Extension {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException("value는 필수입니다");
    }
    value = value.trim().toLowerCase(Locale.ROOT);

    if (!ALLOWED.contains(value)) {
      throw new IllegalArgumentException("지원하지 않는 확장자입니다: " + value);
    }
  }

  public static Extension of(String value) {
    return new Extension(value);
  }

}
