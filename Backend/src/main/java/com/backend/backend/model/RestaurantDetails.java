package com.backend.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class RestaurantDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID restaurantId;
    private String restaurantName;

    @OneToMany(mappedBy = "restaurantDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<MenuItem> menuList = new ArrayList<>();

}
