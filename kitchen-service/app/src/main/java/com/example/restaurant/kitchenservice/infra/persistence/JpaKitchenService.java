package com.example.restaurant.kitchenservice.infra.persistence;

import com.example.restaurant.kitchenservice.application.exception.ErrorCode;
import com.example.restaurant.kitchenservice.application.exception.KitchenServiceException;
import com.example.restaurant.kitchenservice.domain.Chef;
import com.example.restaurant.kitchenservice.domain.Id;
import com.example.restaurant.kitchenservice.domain.Name;
import com.example.restaurant.kitchenservice.domain.service.KitchenService;
import com.example.restaurant.orderservice.application.command.OrderCommandHandler;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class JpaKitchenService implements KitchenService {
    private final ChefRepository chefRepository;
    private final OrderCommandHandler orderCommandHandler;

    public JpaKitchenService(final ChefRepository chefRepository, final OrderCommandHandler orderCommandHandler) {
        this.chefRepository = chefRepository;
        this.orderCommandHandler = orderCommandHandler;
    }

    @Override
    public Chef createChef(Name name) {
        var chefEntity = new ChefEntity(name.value());
        var chef = chefRepository.save(chefEntity);

        return new Chef(
                new Id(chef.getId()),
                new Name(chef.getName()),
                chef.getOrders().stream().map(entity -> new Id(entity.getId())).collect(Collectors.toSet())
        );
    }

    @Override
    public Chef assignChef(long orderId) {
        return this.chefs().stream().filter(Chef::isAvailable).findFirst().map(c -> {
                    final Set<OrderIdEntity> chefOrders = new HashSet<>();
                    chefOrders.add(new OrderIdEntity(orderId));
                    chefOrders.addAll(c.orders().stream().map(id -> new OrderIdEntity(id.value())).collect(Collectors.toSet()));
                    var chefEntity = new ChefEntity(c.id().value(), c.name().value(), chefOrders);
                    chefRepository.save(chefEntity);
                    return c;
                }
        ).orElseThrow(() -> new KitchenServiceException(
                String.format("There are no available chefs to prepare order %d", orderId), ErrorCode.ERR_KIT_SER_2)
        );
    }

    @Override
    public Set<Chef> chefs() {
        var chefs = chefRepository.findAll();
        return StreamSupport.stream(chefs.spliterator(), false).map(
                chef -> new Chef(new Id(chef.getId()), new Name(chef.getName()), chef.getOrders().stream().map(o -> new Id(o.getId())).collect(Collectors.toSet()))
        ).collect(Collectors.toSet());
    }

    @Override
    public Chef chef(Id id) {
        var chef = chefRepository.findById(id.value()).orElseThrow(
                () -> new KitchenServiceException("Could not find chef with id ${id.value}", ErrorCode.ERR_KIT_SER_1)
        );

        return new Chef(
                new Id(chef.getId()),
                new Name(chef.getName()),
                chef.getOrders().stream().map(o -> new Id(o.getId())).collect(Collectors.toSet())
        );
    }
}
