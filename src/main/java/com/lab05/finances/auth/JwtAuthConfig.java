package com.lab05.finances.auth;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtAuthConfig {

    @Bean
    JwtTokenValidator jwtTokenValidator(JwtProperties properties) {
        return new JwtTokenValidator(properties);
    }

    @Bean
    FilterRegistrationBean<JwtAuthFilter> jwtAuthFilterRegistration(JwtTokenValidator validator) {
        FilterRegistrationBean<JwtAuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new JwtAuthFilter(validator));
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        return registration;
    }
}
