package com.fikskamil.springcloudgateway.security.configuration;

import com.fikskamil.springcloudgateway.security.authorization.CustomReactiveAuthorizationManager;
import com.fikskamil.springcloudgateway.security.authentication.CustomServerAuthenticationEntryPoint;
import com.fikskamil.springcloudgateway.security.authorization.CustomServerAccessDeniedHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@AllArgsConstructor
public class SecurityConfig {

    private CustomReactiveAuthorizationManager customReactiveAuthorizationManager;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange()
                .anyExchange()
                .access(customReactiveAuthorizationManager)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new CustomServerAccessDeniedHandler())
                .and()
                .oauth2ResourceServer()
                .authenticationEntryPoint(new CustomServerAuthenticationEntryPoint())
                .jwt();

        return http.build();
    }
}
