package com.sal0m0n.drom.repository.car;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;

    private String description;

    private String state;

    private String color;

    private String mileage;

    private Long ownerId;

}
