package com.hyk.portfolio.common.domain;

import java.net.URI;
import java.net.URISyntaxException;

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
public class Thumbnail {

  @Column(name = "thumbnail", length = 2048)
  private String url;

  public Thumbnail(String url) {
    if (url == null) {
      throw new IllegalArgumentException("Thumbnail URL must not be null");
    }
    try {
      URI uri = new URI(url);
      if (!uri.isAbsolute()) {
        throw new IllegalArgumentException("Thumbnail URL must be an absolute URL: " + url);
      }
    }
    catch (URISyntaxException e) {
      throw new IllegalArgumentException("Thumbnail URL is invalid: " + url, e);
    }
    this.url = url;
  }

}
