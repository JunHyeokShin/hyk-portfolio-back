package com.hyk.portfolio.common.domain;

import java.util.regex.Pattern;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ThemeColor {

  private static final Pattern HEX_COLOR = Pattern.compile("^#([0-9A-Fa-f]{3}|[0-9A-Fa-f]{6})$");

  @Column(name = "theme_color", length = 7)
  private String code;

  public ThemeColor(String code) {
    if (code == null || !HEX_COLOR.matcher(code).matches()) {
      throw new IllegalArgumentException("ThemeColor must be a valid HEX color code: " + code);
    }
    this.code = code;
  }

}
