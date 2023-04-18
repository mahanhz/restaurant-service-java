package com.example.restaurant.kitchenservice;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.example.restaurant.kitchenservice"})
public class KitchenJpaConfig {

}
