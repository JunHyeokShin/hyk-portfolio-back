package com.hyk.portfolio.global.security;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "security")
record ApiKeyProperties(@NotBlank @Size(min = 32) String apiKey) {

}
