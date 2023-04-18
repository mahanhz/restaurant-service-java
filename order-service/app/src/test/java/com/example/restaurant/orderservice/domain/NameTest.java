package com.example.restaurant.orderservice.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NameTest {

    @ParameterizedTest
    @MethodSource("validNames")
    public void should_allow_creation_with_valid_names(final String value) {
        assertThat(new Name(value)).isNotNull();
    }

    @ParameterizedTest
    @MethodSource("invalidNames")
    public void should_not_allow_creation_with_invalid_names(final String value) {
        assertThatThrownBy(() -> {
            new Name(value);
        }).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> validNames() {
        return Stream.of(
                Arguments.of("Jo"),
                Arguments.of("b".repeat(200))
        );
    }

    private static Stream<Arguments> invalidNames() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("a"),
                Arguments.of("b".repeat(201))
        );
    }
}