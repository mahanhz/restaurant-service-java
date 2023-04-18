package com.example.restaurant.orderservice.integrationtest;

import com.example.restaurant.orderservice.domain.State;
import com.example.restaurant.orderservice.infra.persistence.ItemEntity;
import com.example.restaurant.orderservice.infra.persistence.OrderEntity;
import com.example.restaurant.orderservice.infra.persistence.OrderRepository;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;


@DataJpaTest
class OrderRepositoryIT extends DatabaseTest {
	@Autowired TestEntityManager entityManager;
	@Autowired OrderRepository repository;

	@Test
	public void should_create_an_order_with_items() {
		final String orderName = "Burger";
		final LocalDate orderDate = LocalDate.of(2023, 3, 5);

		final ItemEntity itemEntity = new ItemEntity(orderName, 1);
		entityManager.persist(itemEntity);

		final OrderEntity orderEntity = new OrderEntity(orderDate, State.CREATED, Set.of(itemEntity));
		final OrderEntity savedOrder = entityManager.persist(orderEntity);

		final Optional<OrderEntity> order = repository.findById(savedOrder.getId());
		assertThat(order).isNotNull().satisfies(it -> {
			final OrderEntity existingOrder = it.get();
			assertThat(existingOrder.getDate()).isEqualTo(orderDate);
			assertThat(existingOrder.getState()).isEqualTo(State.CREATED);

			final ItemEntity firstItem = existingOrder.getItems().iterator().next();
			assertThat(firstItem.getName()).isEqualTo(orderName);
			assertThat(firstItem.getQuantity()).isEqualTo(1);
		});
	}
}
