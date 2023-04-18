package com.example.restaurant.kitchenservice.steps;

import com.example.restaurant.kitchenservice.application.api.ChefApi;
import com.example.restaurant.kitchenservice.application.api.DetailedChefApi;
import com.example.restaurant.orderservice.application.api.ItemApi;
import com.example.restaurant.orderservice.application.api.OrderApi;
import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java8.En;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;


class KitchenSteps implements En {

    private OrderApi order;
    private ChefApi chef;

    public KitchenSteps(final TestRestTemplate restTemplate) {
        Given("chef {string} is available", (final String chefName) -> {
            var chefResponse = restTemplate.postForEntity(
                    "/chefs",
                    new ChefApi(chefName, Collections.emptySet(), Collections.emptySet()),
                    ChefApi.class
            );
            chef = chefResponse.getBody();
        });

        When("an order for a {string} is created", (String itemName) -> {
            var orderResponse = restTemplate.postForEntity(
                    "/orders",
                    new OrderApi(LocalDate.now(), Set.of(new ItemApi(itemName, 1))),
                    OrderApi.class
            );
            order = orderResponse.getBody();
        });

        Then("the order for a {string} is assigned to chef {string}", (final String itemName, final String chefName) -> {
            var chefResponse = restTemplate.getForEntity(
                    String.format("/chefs/%d", chef.id()),
                    DetailedChefApi.class
            );

            final DetailedChefApi body = chefResponse.getBody();
            assertThat(body.chef()).isEqualTo(chefName);
            assertThat(body.orders()).hasSize(1);
            assertThat(body.orders().stream().findFirst().get().id()).isEqualTo(order.id());
            assertThat(body.orders().stream().findFirst().get().name()).isEqualTo(itemName);
        });
    }
}
