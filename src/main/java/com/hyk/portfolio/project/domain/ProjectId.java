package com.hyk.portfolio.project.domain;

import java.io.Serializable;
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
public class ProjectId implements Serializable {

  @Column(name = "id", nullable = false, updatable = false)
  private String id;

  private static final Pattern KEBAB_CASE = Pattern.compile("^[a-z0-9]+(-[a-z0-9]+)*$");

  public ProjectId(String id) {
    if (id == null || !KEBAB_CASE.matcher(id).matches()) {
      throw new IllegalArgumentException("ProjectId must be kebab-case: " + id);
    }
    this.id = id;
  }

}
