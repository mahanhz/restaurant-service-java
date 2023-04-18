package com.example.restaurant.kitchenservice.steps;

import io.cucumber.java8.En;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;


public class HealthSteps implements En {
    private ResponseEntity<String> response;

    public HealthSteps(final TestRestTemplate restTemplate) {

        When("^calling the health endpoint$", () -> {
            response = restTemplate.getForEntity("/actuator/health", String.class);
        });

        Then("^the response should be healthy$", () -> {
            assertThat(response.getStatusCode().value()).isEqualTo(200);
        });
    }
}
