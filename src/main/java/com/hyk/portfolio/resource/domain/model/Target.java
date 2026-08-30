package com.hyk.portfolio.resource.domain.model;

public record Target(TargetType type, Long id) {

  public Target {
    if (type == null) {
      throw new IllegalArgumentException("type은 필수입니다");
    }
    if (id == null) {
      throw new IllegalArgumentException("id는 필수입니다");
    }
  }

  public static Target of(TargetType type, Long id) {
    return new Target(type, id);
  }

}
