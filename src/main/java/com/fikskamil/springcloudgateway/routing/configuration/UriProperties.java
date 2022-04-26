package com.fikskamil.springcloudgateway.routing.configuration;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "httpbin")
public class UriProperties {

    private String httpBin = "http://httpbin.org:80";

    private final String targetServiceUri;

    public UriProperties(@Value("${target-service-uri}") String targetServiceUri) {
        this.targetServiceUri = targetServiceUri;
    }

    public String getHttpBin() {
        return httpBin;
    }

    public void setHttpBin(String httpBin) {
        this.httpBin = httpBin;
    }

    public String getTargetServiceUri() {
        return targetServiceUri;
    }
}
