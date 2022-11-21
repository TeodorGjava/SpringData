package com.softuni.cardealer.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {
    @Column
    private String name;
    @Column
    private String birthDate;
    @Column(name = "is_young_driver")
    private Boolean isYoungDriver;
    @OneToMany(targetEntity = Sale.class, mappedBy = "customer")
    @Fetch(FetchMode.JOIN)
    private Set<Sale> sales;

}

