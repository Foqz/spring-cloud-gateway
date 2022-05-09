package com.fikskamil.springcloudgateway.security.opa.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class RequestLoggingInterceptor implements ClientHttpRequestInterceptor {
    private final ObjectMapper objectMapper;
    private final ObjectWriter objectWriter;

    public RequestLoggingInterceptor(ObjectMapper objectMapper,
                                     @Value("${logging.pretty-print.enabled:false}") boolean prettyPrint) {
        this.objectMapper = objectMapper;
        this.objectWriter = prettyPrint ? objectMapper.writerWithDefaultPrettyPrinter() : objectMapper.writer();
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request,
                                        byte[] body,
                                        ClientHttpRequestExecution execution)
            throws IOException {
        log.info("REQ {} {} - {}", request.getMethod(), request.getURI(), getPrettyPrintBodyIfAvailable(body));
        var response = execution.execute(request, body);
        log.info("RES {} - {}", response.getStatusCode(),
                getPrettyPrintBodyIfAvailable(response.getBody().readAllBytes()));
        return response;
    }

    private String getPrettyPrintBodyIfAvailable(byte[] body) {
        try {
            var node = objectMapper.readTree(body);
            return objectWriter.writeValueAsString(node);
        } catch (IOException e) {
            return new String(body);
        }
    }
}
