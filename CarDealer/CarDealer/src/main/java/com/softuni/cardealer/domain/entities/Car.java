package com.softuni.cardealer.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigInteger;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "cars")
public class Car extends BaseEntity {
    @Column
    private String make;
    @Column
    private String model;
    @Column(name = "travelled_distance")
    private Long travelledDistance;
    @ManyToMany(mappedBy = "cars")
    private Set<Part> parts;

}
