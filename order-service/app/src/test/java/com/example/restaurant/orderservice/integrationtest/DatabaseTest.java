package com.example.restaurant.orderservice.integrationtest;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Transactional
@Testcontainers
abstract class DatabaseTest implements IntegrationTest {

    static DockerImageName dockerImageName = DockerImageName.parse("mysql:8.0");
    static MySQLContainer<?> container = new MySQLContainer<>(dockerImageName);

    static {
        container.start();
    }

    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }
}