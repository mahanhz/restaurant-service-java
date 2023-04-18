package com.example.restaurant.kitchenservice.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ChefTest {

    @ParameterizedTest
    @MethodSource("validChefs")
    public void should_create_a_chef(final long id, final String name, final long orderId) {
        assertThat(new Chef(new Id(id), new Name(name), Set.of(new Id(orderId)))).isNotNull();
    }

    @ParameterizedTest
    @MethodSource("invalidChefs")
    public void should_not_create_a_chef(final long id, final String name, final long orderId, final String message) {
        assertThatThrownBy(() -> {
            new Chef(new Id(id), new Name(name), Set.of(new Id(orderId)));
        }).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> validChefs() {
        return Stream.of(
                Arguments.of(1, "Jo", 4),
                Arguments.of(200, "b".repeat(200), 100)
        );
    }

    private static Stream<Arguments> invalidChefs() {
        return Stream.of(
                Arguments.of(1, "", 4, "Name cannot be blank"),
                Arguments.of(2, "a", 1,"Name must be at least 2 characters and no more than 200"),
                Arguments.of(100, "b".repeat(201), 200, "Name must be at least 2 characters and no more than 200")
        );
    }
}