package com.fikskamil.springcloudgateway.security.opa.configuration;


import com.fikskamil.springcloudgateway.security.opa.logging.RequestLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Bean
    RestTemplate restTemplate(RequestLoggingInterceptor requestLoggingInterceptor) {
        var factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        var restTemplate = new RestTemplate(factory);
        restTemplate.getInterceptors()
                .add(requestLoggingInterceptor);
        return restTemplate;
    }

}
