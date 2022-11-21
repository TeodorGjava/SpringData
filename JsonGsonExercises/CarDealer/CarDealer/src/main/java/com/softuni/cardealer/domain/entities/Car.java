package com.softuni.cardealer.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigInteger;
import java.util.*;

@Setter
@Getter

@Entity
@Table(name = "cars")
public class Car extends BaseEntity {
    @Column
    private String make;
    @Column
    private String model;
    @Column(name = "travelled_distance")
    private Long travelledDistance;
    @ManyToMany
    Set<Part> parts;

    public Car() {
        this.parts = new HashSet<>();
    }

    public Car(String make, String model, Long travelledDistance) {
        this();
        this.make = make;
        this.model = model;
        this.travelledDistance = travelledDistance;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(make, car.make)
                && Objects.equals(model, car.model)
                && Objects.equals(travelledDistance, car.travelledDistance)
                && Objects.equals(getId(), car.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(make, model, travelledDistance, getId());
    }
}
