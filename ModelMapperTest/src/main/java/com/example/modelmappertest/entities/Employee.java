package com.example.modelmappertest.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "employees")
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(nullable = false)
    private BigDecimal salary;
    @Column(nullable = false)
    private LocalDate birthday;
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    private Address address;

    public Employee(String firstName, BigDecimal salary, Address address) {
        this.firstName = firstName;
        this.salary = salary;
        this.address = address;
    }
}
