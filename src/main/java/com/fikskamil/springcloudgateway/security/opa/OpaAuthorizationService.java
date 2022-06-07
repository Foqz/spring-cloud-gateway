package com.fikskamil.springcloudgateway.security.opa;

import com.fasterxml.jackson.databind.JsonNode;
import com.fikskamil.springcloudgateway.security.opa.client.OpaAuthorizationClient;
import com.fikskamil.springcloudgateway.security.opa.dto.OpaAuthorizationDto;
import com.fikskamil.springcloudgateway.security.opa.dto.OpaRestAuthorizationInput;
import com.fikskamil.springcloudgateway.security.opa.utils.OpaPathConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpaAuthorizationService {
    private final OpaAuthorizationClient authenticationClient;
    private final OpaPathConverter opaPathConverter;


    public boolean checkRestEndpointAuthorization(String method,
                                                  String path,
                                                  JsonNode token) {
        var input = new OpaRestAuthorizationInput(method, opaPathConverter.convert(path), token);
        var requestBody = new OpaAuthorizationDto(input);
        var responseDto = authenticationClient.authorizeRequestInOpa(requestBody);
        return responseDto.result() != null && responseDto.result();
    }
}

