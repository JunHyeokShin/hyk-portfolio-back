package com.hyk.portfolio.global.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

class ApiKeyAuthenticationProvider implements AuthenticationProvider {

  private static final String PRINCIPAL = "admin";

  private static final List<GrantedAuthority> AUTHORITIES =
      List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));

  private final byte[] apiKey;

  ApiKeyAuthenticationProvider(ApiKeyProperties properties) {
    this.apiKey = properties.apiKey().getBytes(StandardCharsets.UTF_8);
  }

  @Override
  public Authentication authenticate(Authentication authentication) {
    if (!(authentication.getCredentials() instanceof String presented)) {
      throw new BadCredentialsException("API 키가 없습니다");
    }
    if (!MessageDigest.isEqual(presented.getBytes(StandardCharsets.UTF_8), this.apiKey)) {
      throw new BadCredentialsException("API 키가 올바르지 않습니다");
    }
    return ApiKeyAuthenticationToken.authenticated(PRINCIPAL, AUTHORITIES);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return ApiKeyAuthenticationToken.class.isAssignableFrom(authentication);
  }

}
