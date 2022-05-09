package com.fikskamil.springcloudgateway.security.opa.dto;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public record OpaRestAuthorizationInput(String method, List<String> path, JsonNode token) implements OpaAuthorizationInput {
}
