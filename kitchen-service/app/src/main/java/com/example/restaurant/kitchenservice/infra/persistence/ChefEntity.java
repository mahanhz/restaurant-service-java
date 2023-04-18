package com.example.restaurant.kitchenservice.infra.persistence;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "chefs")
public class ChefEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @Column(nullable = false) private String name;

    @OneToMany(targetEntity = OrderIdEntity.class)
    @JoinTable(
            name = "chefs_orders",
            joinColumns = @JoinColumn(name = "chef_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id")
    ) private Set<OrderIdEntity> orders = new HashSet<>();

    @CreatedDate @Column(name = "created_date") private LocalDateTime createdDate = LocalDateTime.now();
    @LastModifiedDate @Column(name = "last_modified_date") private LocalDateTime lastModifiedDate = LocalDateTime.now();

    public ChefEntity() {
    }

    public ChefEntity(String name) {
        this.name = name;
    }

    public ChefEntity(Long id, String name, Set<OrderIdEntity> orders) {
        this.id = id;
        this.name = name;
        this.orders = orders;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<OrderIdEntity> getOrders() {
        return orders;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrders(Set<OrderIdEntity> orders) {
        this.orders = orders;
    }
}
