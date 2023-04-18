package com.example.restaurant.kitchenservice.infra.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ChefRepository extends CrudRepository<ChefEntity, Long> {
    Optional<ChefEntity> findByName(String name);
}
