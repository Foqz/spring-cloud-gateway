package com.fikskamil.springcloudgateway.security.opa.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "opa.data")
public class OpaDataConfiguration {
    private String restEndpoint;
}
