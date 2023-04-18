package com.example.restaurant.orderservice.config;

import com.example.restaurant.orderservice.model.World;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@TestConfiguration
public class WorldConfig {

    @Scope("cucumber-glue")
    @Bean
    public World world() {
        return new World();
    }
}
