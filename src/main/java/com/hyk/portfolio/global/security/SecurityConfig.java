package com.hyk.portfolio.global.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.HandlerExceptionResolver;

@EnableConfigurationProperties({ ApiKeyProperties.class, CorsProperties.class })
@EnableMethodSecurity
@Configuration
class SecurityConfig {

  @Bean
  AuthenticationManager authenticationManager(ApiKeyProperties apiKeyProperties) {
    return new ProviderManager(new ApiKeyAuthenticationProvider(apiKeyProperties));
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource(CorsProperties corsProperties) {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(corsProperties.allowedOrigins());
    configuration.setAllowedMethods(
        List.of(
            HttpMethod.GET.name(),
            HttpMethod.POST.name(),
            HttpMethod.PUT.name(),
            HttpMethod.DELETE.name()));
    configuration.setAllowedHeaders(
        List.of(HttpHeaders.CONTENT_TYPE, ApiKeyAuthenticationConverter.API_KEY_HEADER));
    configuration.setExposedHeaders(List.of(HttpHeaders.LOCATION));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  SecurityFilterChain securityFilterChain(
      HttpSecurity http,
      AuthenticationManager authenticationManager,
      @Qualifier("handlerExceptionResolver") HandlerExceptionResolver handlerExceptionResolver
  ) {
    AuthenticationEntryPoint entryPoint = (request, response, authException) ->
        handlerExceptionResolver.resolveException(request, response, null, authException);
    return http
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .cors(Customizer.withDefaults())
        .csrf(AbstractHttpConfigurer::disable)
        .addFilter(apiKeyAuthenticationFilter(authenticationManager, entryPoint))
        .exceptionHandling(
            exception -> exception
                .authenticationEntryPoint(entryPoint)
                .accessDeniedHandler((request, response, accessDeniedException) ->
                    handlerExceptionResolver.resolveException(
                        request, response, null, accessDeniedException)))
        .authorizeHttpRequests(
            authorize -> authorize
                .requestMatchers(HttpMethod.POST, "/projects", "/resources").authenticated()
                .requestMatchers(HttpMethod.PUT, "/projects/{slug}").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/projects/{slug}").authenticated()
                .anyRequest().permitAll())
        .build();
  }

  private AuthenticationFilter apiKeyAuthenticationFilter(
      AuthenticationManager authenticationManager, AuthenticationEntryPoint entryPoint) {
    AuthenticationFilter filter =
        new AuthenticationFilter(authenticationManager, new ApiKeyAuthenticationConverter());
    filter.setSuccessHandler((request, response, authentication) -> {
    });
    filter.setFailureHandler(new AuthenticationEntryPointFailureHandler(entryPoint));
    return filter;
  }

}
