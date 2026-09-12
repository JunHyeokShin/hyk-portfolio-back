package com.hyk.portfolio.global.security;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;

class ApiKeyAuthenticationConverter implements AuthenticationConverter {

  static final String API_KEY_HEADER = "X-API-Key";

  private final AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource =
      new WebAuthenticationDetailsSource();

  @Override
  public Authentication convert(HttpServletRequest request) {
    String apiKey = request.getHeader(API_KEY_HEADER);
    if (apiKey == null) {
      return null;
    }
    if (!StringUtils.hasText(apiKey)) {
      throw new BadCredentialsException("API 키가 비어 있습니다");
    }
    ApiKeyAuthenticationToken authentication = ApiKeyAuthenticationToken.unauthenticated(apiKey);
    authentication.setDetails(this.authenticationDetailsSource.buildDetails(request));
    return authentication;
  }

}
