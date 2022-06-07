package com.fikskamil.springcloudgateway.security.opa.client;

import com.fikskamil.springcloudgateway.security.opa.configuration.OpaDataConfiguration;
import com.fikskamil.springcloudgateway.security.opa.dto.OpaAuthorizationDto;
import com.fikskamil.springcloudgateway.security.opa.dto.OpaResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OpaAuthorizationClient {
    private final OpaDataConfiguration configuration;
    private final RestTemplate restTemplate;

    public OpaResponseDto authorizeRequestInOpa(OpaAuthorizationDto authenticationDto) {
        return restTemplate.postForObject(configuration.getRestEndpoint(), authenticationDto, OpaResponseDto.class);
    }
}
