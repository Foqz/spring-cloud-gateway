package com.fikskamil.springcloudgateway.routing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"httbin=http://localhost:${wiremock.server.port}"})
@AutoConfigureWireMock(port = 0)
class RoutingConfigurationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testRouteLocator() throws Exception {
//        Example for application tests without authentication and authorization
//        stubFor(get(urlEqualTo("/get"))
//                .willReturn(aResponse()
//                        .withBody("{\"headers\":{\"Hello\":\"World\"}}")
//                        .withHeader("Content-Type", "application/json")));
//        stubFor(get(urlEqualTo("/delay/3"))
//                .willReturn(aResponse()
//                        .withBody("no fallback")
//                        .withFixedDelay(3000)));
//
//        webTestClient
//                .get().uri("/get")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody()
//                .jsonPath("$.headers.Hello").isEqualTo("World");
//
//        webTestClient
//                .get().uri("/delay/3")
//                .header("Host", "www.circuitbreaker.com")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody()
//                .consumeWith(
//                        response -> assertThat(response.getResponseBody()).isEqualTo("fallback".getBytes()));
    }
}
