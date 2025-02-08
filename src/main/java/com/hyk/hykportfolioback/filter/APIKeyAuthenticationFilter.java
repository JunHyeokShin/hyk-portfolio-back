package com.hyk.hykportfolioback.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class APIKeyAuthenticationFilter extends OncePerRequestFilter {

  private final String expectedAPIKey;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String receivedAPIKey = request.getHeader("API-KEY");

    if (expectedAPIKey.equals(receivedAPIKey)) {
      Authentication authentication = new APIKeyAuthentication(receivedAPIKey);
      SecurityContextHolder.getContext().setAuthentication(authentication);

      filterChain.doFilter(request, response);
      return;
    }

    filterChain.doFilter(request, response);
  }

  private static class APIKeyAuthentication extends AbstractAuthenticationToken {

    private final String apiKey;

    public APIKeyAuthentication(String apiKey) {
      super(AuthorityUtils.NO_AUTHORITIES);
      this.apiKey = apiKey;
      setAuthenticated(true);
    }


    @Override
    public Object getCredentials() {
      return null;
    }

    @Override
    public Object getPrincipal() {
      return apiKey;
    }

  }

}
