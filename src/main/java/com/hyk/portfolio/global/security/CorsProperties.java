package com.hyk.portfolio.global.security;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "security.cors")
record CorsProperties(@NotEmpty List<String> allowedOrigins) {

}
