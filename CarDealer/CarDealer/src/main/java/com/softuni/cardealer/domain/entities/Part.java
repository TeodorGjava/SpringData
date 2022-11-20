package com.softuni.cardealer.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;


@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "parts")
public class Part extends BaseEntity {
    @Column
    private String name;
    @Column
    private BigDecimal price;
    @Column
    private Integer quantity;
    @ManyToOne
    private Supplier supplier;
    @ManyToMany
    private Set<Car> cars;

}
