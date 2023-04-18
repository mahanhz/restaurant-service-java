package com.example.restaurant.kitchenservice.domain.service;

import com.example.restaurant.kitchenservice.domain.Chef;
import com.example.restaurant.kitchenservice.domain.Id;
import com.example.restaurant.kitchenservice.domain.Name;

import java.util.Set;

public interface KitchenService {

    Chef createChef(Name name);
    Chef assignChef(long orderId);

    Set<Chef> chefs();
    Chef chef(Id id);
}
