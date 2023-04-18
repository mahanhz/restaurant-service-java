package com.example.restaurant.kitchenservice.infra.web;

import com.example.restaurant.kitchenservice.application.api.ChefApi;
import com.example.restaurant.kitchenservice.application.api.DetailedChefApi;
import com.example.restaurant.kitchenservice.application.command.KitchenCommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class KitchenController {

    private static final Logger LOGGER = LoggerFactory.getLogger(KitchenController.class);

    private final KitchenCommandHandler kitchenCommandHandler;

    public KitchenController(final KitchenCommandHandler kitchenCommandHandler) {
        this.kitchenCommandHandler = kitchenCommandHandler;
    }

    @PostMapping("/chefs")
    public ResponseEntity<ChefApi> createChef(@RequestBody ChefApi chefApi) {
        final ChefApi chef = kitchenCommandHandler.createChef(chefApi.chef());

        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(chef.id()).toUri();

        return ResponseEntity.created(uri).body(chef);
    }

    @GetMapping("/chefs/{id}")
    public ResponseEntity<DetailedChefApi> chef(@PathVariable Long id) {
        final DetailedChefApi chef = kitchenCommandHandler.chef(id);

        return ResponseEntity.ok(chef);
    }

    @PostMapping("/chefs/assign")
    public ResponseEntity<ChefApi> assignChef(@RequestBody long orderId) {
        final ChefApi chef = kitchenCommandHandler.assignChef(orderId);

        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(chef.id()).toUri();

        return ResponseEntity.created(uri).body(chef);
    }
}
