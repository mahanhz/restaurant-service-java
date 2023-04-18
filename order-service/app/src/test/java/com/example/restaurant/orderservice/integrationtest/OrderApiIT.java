package com.example.restaurant.orderservice.integrationtest;

import com.example.restaurant.orderservice.application.api.ItemApi;
import com.example.restaurant.orderservice.application.api.OrderApi;
import com.example.restaurant.orderservice.domain.State;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOError;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@JsonTest
class OrderApiIT implements IntegrationTest {
    @Autowired
    private JacksonTester<OrderApi> json;

    @Test
    public void should_serialize() throws IOException {
        final OrderApi orderApi = new OrderApi(
                1234L,
                State.CREATED.toString(),
                LocalDate.of(2023, 3, 5),
                Set.of(new ItemApi("Burger", 1))
        );

        assertThat(json.write(orderApi)).extractingJsonPathNumberValue("@.id").isEqualTo(1234);
        assertThat(json.write(orderApi)).extractingJsonPathStringValue("@.date").isEqualTo("2023-03-05");
        assertThat(json.write(orderApi)).extractingJsonPathStringValue("@.items[0].name").isEqualTo("Burger");
        assertThat(json.write(orderApi)).extractingJsonPathNumberValue("@.items[0].quantity").isEqualTo(1);
        assertThat(json.write(orderApi)).extractingJsonPathStringValue("@.state").isEqualTo("CREATED");
    }

    @Test
    public void should_deserialize() throws IOException {
        final String content = "{\"id\":1234,\"date\":\"2023-03-05\",\"state\":\"CREATED\",\"items\":[{\"name\":\"Burger\",\"quantity\":1}]}";
        assertThat(json.parseObject(content)).isEqualTo(
                new OrderApi(
                        1234L,
                        State.CREATED.toString(),
                        LocalDate.of(2023, 3, 5),
                        Set.of(new ItemApi("Burger", 1))
                )
        );
    }
}