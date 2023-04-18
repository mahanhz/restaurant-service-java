package com.example.restaurant.orderservice;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.example.restaurant.orderservice"})
public class OrderJpaConfig {

}
