package com.hyk.portfolio.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record AppProperties(

    Error error

) {

  public record Error(

      String docBaseUrl

  ) {

  }

}
