package com.hyk.portfolio.project.domain.model;

import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public record Content(String value) {

  private static final Pattern URL_PATTERN = Pattern.compile("https?://[^\\s)\"'<>]+");

  public Content {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException("value는 필수입니다");
    }
  }

  public static Content of(String value) {
    return new Content(value);
  }

  public Set<String> referencedUrls() {
    return URL_PATTERN.matcher(this.value)
        .results()
        .map(MatchResult::group)
        .collect(Collectors.toUnmodifiableSet());
  }

}
