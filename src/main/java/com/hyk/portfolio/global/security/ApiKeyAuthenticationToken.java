package com.hyk.portfolio.global.security;

import java.io.Serial;
import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

class ApiKeyAuthenticationToken extends AbstractAuthenticationToken {

  @Serial
  private static final long serialVersionUID = 1L;

  private final Object principal;

  private String apiKey;

  private ApiKeyAuthenticationToken(String apiKey) {
    super((Collection<? extends GrantedAuthority>) null);
    this.principal = null;
    this.apiKey = apiKey;
    super.setAuthenticated(false);
  }

  private ApiKeyAuthenticationToken(
      Object principal, Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.principal = principal;
    this.apiKey = null;
    super.setAuthenticated(true);
  }

  static ApiKeyAuthenticationToken unauthenticated(String apiKey) {
    return new ApiKeyAuthenticationToken(apiKey);
  }

  static ApiKeyAuthenticationToken authenticated(
      Object principal, Collection<? extends GrantedAuthority> authorities) {
    return new ApiKeyAuthenticationToken(principal, authorities);
  }

  @Override
  public Object getCredentials() {
    return this.apiKey;
  }

  @Override
  public Object getPrincipal() {
    return this.principal;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) {
    Assert.isTrue(!isAuthenticated, "인증된 상태로 직접 변경할 수 없습니다");
    super.setAuthenticated(false);
  }

  @Override
  public void eraseCredentials() {
    super.eraseCredentials();
    this.apiKey = null;
  }

}
