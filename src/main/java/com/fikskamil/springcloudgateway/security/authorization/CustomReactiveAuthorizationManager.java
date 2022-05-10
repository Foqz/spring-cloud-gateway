package com.fikskamil.springcloudgateway.security.authorization;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fikskamil.springcloudgateway.security.opa.OpaAuthorizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomReactiveAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final OpaAuthorizationService opaAuthorizationService;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext authorizationContext) {

        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        String path = request.getURI().getPath();
        String method = Optional.of(request.getMethodValue()).orElse(StringUtils.EMPTY);

        return authentication
                .filter(Authentication::isAuthenticated)
                .filter(a -> a instanceof JwtAuthenticationToken)
                .cast(JwtAuthenticationToken.class)
                .map(jwtAuthenticationToken -> authorize(method, path, jwtAuthenticationToken));
    }

    private AuthorizationDecision authorize(String method, String path, JwtAuthenticationToken jwtAuthenticationToken) {
        JsonNode decodedToken;

        try {
            decodedToken = decodeTokenToJsonNode(jwtAuthenticationToken);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return new AuthorizationDecision(false);
        }

        boolean result = opaAuthorizationService.checkRestEndpointAuthorization(method, path, decodedToken);
        log.debug("Request {} {} got OPA vote {}", method, path, result);
        return result ? new AuthorizationDecision(true) : new AuthorizationDecision(false);
    }

    private JsonNode decodeTokenToJsonNode(JwtAuthenticationToken jwtAuthenticationToken) throws JsonProcessingException {
        DecodedJWT decodedJWT = JWT.decode(jwtAuthenticationToken.getToken().getTokenValue());
        return objectMapper.readTree(
                new String(Base64.getDecoder().decode(decodedJWT.getPayload()), StandardCharsets.UTF_8)
        );
    }
}
