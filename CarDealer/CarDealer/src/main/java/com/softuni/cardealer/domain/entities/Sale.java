package com.softuni.cardealer.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "sales")
public class Sale extends BaseEntity {
    @ManyToOne
    private Car car;
    @ManyToOne
    private Customer customer;
    @Column(name = "discount")
    private BigDecimal discount;

}
