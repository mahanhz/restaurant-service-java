package com.example.restaurant.orderservice.integrationtest;

import com.example.restaurant.orderservice.application.api.ItemApi;
import com.example.restaurant.orderservice.application.api.OrderApi;
import com.example.restaurant.orderservice.application.command.OrderCommandHandler;
import com.example.restaurant.orderservice.domain.State;
import com.example.restaurant.orderservice.infra.web.OrderController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(OrderController.class)
class OrderControllerIT implements IntegrationTest {
	private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OrderCommandHandler commandHandler;


	@Test
	public void should_create_order() throws Exception {
		final OrderApi orderApiRequest = new OrderApi(
				LocalDate.of(2023, 3, 5),
				Set.of(new ItemApi("Burger", 1))
		);

		final OrderApi orderApiResponse = new OrderApi(
				1234L,
				State.CREATED.toString(),
				LocalDate.of(2023, 3, 5),
				Set.of(new ItemApi("Burger", 1))
		);

		given(commandHandler.createOrder(orderApiRequest)).willReturn(orderApiResponse);

		mockMvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON).content(MAPPER.writeValueAsString(orderApiRequest)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value("1234"))
				.andExpect(jsonPath("$.date").value("2023-03-05"))
				.andExpect(jsonPath("$.items[0].name").value("Burger"))
				.andExpect(jsonPath("$.items[0].quantity").value("1"))
				.andExpect(jsonPath("$.state").value("CREATED"));
	}
}
