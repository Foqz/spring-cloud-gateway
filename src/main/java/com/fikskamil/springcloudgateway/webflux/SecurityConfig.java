package com.fikskamil.springcloudgateway.webflux;

import com.fikskamil.springcloudgateway.webflux.authorization.CustomReactiveAuthorizationManager;
import com.fikskamil.springcloudgateway.webflux.errorhandler.CustomServerAuthenticationEntryPoint;
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
                .csrf().disable()
                .authorizeExchange()
                .anyExchange()
                .access(customReactiveAuthorizationManager)
                .and()
                .oauth2ResourceServer()
                .authenticationEntryPoint(new CustomServerAuthenticationEntryPoint())
                .jwt();

        return http.build();
    }
}
