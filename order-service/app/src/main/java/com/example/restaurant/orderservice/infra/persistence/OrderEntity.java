package com.example.restaurant.orderservice.infra.persistence;

import com.example.restaurant.orderservice.domain.State;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="order_date", nullable = false)
    private LocalDate date;

    @Column(name="state", nullable = false) @Enumerated(EnumType.STRING)
    State state;

    @ManyToMany(targetEntity = ItemEntity.class)
    @JoinTable(
            name = "orders_items",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    ) private Set<ItemEntity> items;

    @CreatedDate
    @Column(name = "created_date") private LocalDateTime createdDate = LocalDateTime.now();
    @LastModifiedDate
    @Column(name = "last_modified_date") private LocalDateTime lastModifiedDate = LocalDateTime.now();

    public OrderEntity() {
    }

    public OrderEntity(LocalDate date, State state, Set<ItemEntity> items) {
        this.date = date;
        this.state = state;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Set<ItemEntity> getItems() {
        return items;
    }

    public void setItems(Set<ItemEntity> items) {
        this.items = items;
    }
}
