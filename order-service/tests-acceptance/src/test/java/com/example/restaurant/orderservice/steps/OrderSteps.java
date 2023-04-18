package com.example.restaurant.orderservice.steps;

import com.example.restaurant.orderservice.application.api.ItemApi;
import com.example.restaurant.orderservice.application.api.OrderApi;
import io.cucumber.java8.En;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


public class OrderSteps implements En {

    private ResponseEntity<OrderApi> response;

    public OrderSteps(final TestRestTemplate restTemplate) {
        When("creating an order for item {string} and date {string}", (final String name, final String date) -> {
            response = restTemplate.postForEntity(
                    "/orders",
                    new OrderApi(LocalDate.parse(date), Set.of(new ItemApi(name, 1))),
                    OrderApi.class
            );
        });

        Then("the order should be created", () -> {
            assertThat(response.getBody().state()).isEqualTo("CREATED");
        });

        Then("the order should contain the item {string} and date {string}", (final String name, final String date) -> {
            assertThat(response.getBody().date()).isEqualTo(date);

            final ItemApi firstItem = response.getBody().items().iterator().next();
            assertThat(firstItem.name()).isEqualTo(name);
            assertThat(firstItem.quantity()).isEqualTo(1);
        });

        Then("the order should have an identity", () -> {
            assertThat(response.getBody().id()).isNotNull();
        });
    }
}
