package com.example.restaurant.kitchenservice.domain;

import static org.apache.commons.lang3.StringUtils.trim;
import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notBlank;

public record Name(String value) {
    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 200;

    public Name(String value) {
        this.value = checkName(value);
    }

    private String checkName(final String value) {
        notBlank(value);
        final String trimmed = trim(value);
        isTrue(trimmed.length() >= MIN_LENGTH && trimmed.length() <= MAX_LENGTH,
                "Name must be at least %d characters and no more than %d",
                MIN_LENGTH, MAX_LENGTH);

        return trimmed;
    }
}
